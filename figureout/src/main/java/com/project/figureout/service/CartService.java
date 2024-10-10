package com.project.figureout.service;

import com.project.figureout.dto.CartProductDTO;
import com.project.figureout.dto.ChangeCartProductQuantityDTO;
import com.project.figureout.dto.StockDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.repository.ClientRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartsProductsRepository cartsProductsRepository;

    public Cart getCartById(long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado com base no ID."));
    }

    public Client getClientByCart(Cart cart) {
        return cart.getClient();
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteProductFromCart(Cart cart, Product product) {
        cart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().getId() == (product.getId()));
        setCartTotal(cart);
    }

    public void addProductToCart(Cart cart, Product product, ChangeCartProductQuantityDTO changeCartProductQuantityDTO) {
        CartsProductsKey cartsProductsKey = new CartsProductsKey(cart.getId(), product.getId());

        CartsProducts cartProduct = new CartsProducts();
        cartProduct.setId(cartsProductsKey);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setProductQuantity(changeCartProductQuantityDTO.getQuantity());
        cartProduct.setPriceToPay(product.getPrice()); // setting a temporary price here so it doesnt come out as 0 when i check for promotional coupon

        if(cart.getPromotionalCoupon() != null) {
            setPromotionalCouponDiscount(cartProduct, cart.getPromotionalCoupon());
        } else {
            cartProduct.setPriceToPay(product.getPrice());
        }

        LocalDateTime now = LocalDateTime.now();
        cartProduct.setProductAddedTime(now);

        cart.getCartProducts().add(cartProduct);

        setCartTotal(cart);
    }

    public void setPromotionalCouponDiscount(CartsProducts cartProduct, PromotionalCoupon promotionalCoupon) {
        BigDecimal cartProductOldPrice = cartProduct.getPriceToPay();

        BigDecimal newPrice = cartProductOldPrice.subtract(cartProductOldPrice.multiply(promotionalCoupon.getCouponDiscount()));

        cartProduct.setPriceToPay(newPrice);
    }

    public void applyPromotionalCoupon(Cart cart, PromotionalCoupon promotionalCoupon) {
        List<CartsProducts> cartsProducts = cart.getCartProducts();

        if(cart.getPromotionalCoupon() != null) {
            System.out.println("sussybaka.. already applied a coupon didnt you?!");

            for(CartsProducts cartProduct: cartsProducts) {
                cartProduct.setPriceToPay(cartProduct.getPriceToPay().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
            }

        }

        cart.setPromotionalCoupon(promotionalCoupon);

        for(CartsProducts cartProduct: cartsProducts) {
            setPromotionalCouponDiscount(cartProduct, promotionalCoupon);
        }

        setCartTotal(cart);
    }

    public void applyFreight(Cart cart, BigDecimal freight) {

    }

    public void setCartTotal(Cart cart) {
        BigDecimal total = new BigDecimal(0);
        System.out.println("calling setCartTotal");

        // Get the list of products in the cart
        List<CartsProducts> cartProducts = cart.getCartProducts();

        // Loop through each product in the cart and calculate the total price
        for (CartsProducts cartsProduct : cartProducts) {
            BigDecimal productTotal = cartsProduct.getPriceToPay().multiply(BigDecimal.valueOf(cartsProduct.getProductQuantity()));
            System.out.println(productTotal);
            total = total.add(productTotal);
        }

        // Set the total price in the cart
        cart.setTotalPrice(total);

        // Save the cart with the updated total
        saveCart(cart);
    }

    @Transactional
    public void changeClientCart(Client client) {
        LocalDateTime now = LocalDateTime.now();
        Cart newCart = new Cart(now);

        for(Cart currentCart: client.getCartList()) {
            System.out.println("Setting " + currentCart.getId() + " cart to false");
            currentCart.setBeingUsed(false);
            saveCart(currentCart);
        }

        client.getCartList().add(newCart);

        newCart.setClient(client);
        newCart.setBeingUsed(true);

        saveCart(newCart);
    }

    public Client getClientWithCarts(long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Hibernate.initialize(client.getCartList());  // Ensure the cartList is initialized within the session
        return client;
    }

    public void expireClientCart(long id) {
        Client client = getClientWithCarts(id);
        System.out.println(client.getName());

        // Create a copy of the cartList to avoid modifying the original list while iterating
        List<Cart> cartListCopy = new ArrayList<>(client.getCartList());

        for (Cart currentCart : cartListCopy) {
            if (currentCart.isBeingUsed()) {

                List<CartsProducts> cartProductsCopy = new ArrayList<>(currentCart.getCartProducts());

                if(cartProductsCopy.isEmpty()) {
                    return;
                }

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime expirationTime = currentCart.getDateOfCreation().plusMinutes(1);

                if (now.isAfter(expirationTime)) {
                    System.out.println("Cart expired");

                    changeClientCart(client);  // Handle expired cart
                }
            }
        }

    }

    private static final int qntMinutesExpire = 20;  // Set to 20 minutes
    private static final long qntMinutesExpireInMs = qntMinutesExpire * 60 * 1000; // Convert to milliseconds

    @Scheduled(fixedRate = qntMinutesExpireInMs)  // Run every minute (60000 ms)
    public void checkForExpiredCarts() {

        List<Client> clients = clientRepository.findAll();

        if (!clients.isEmpty()) {
            for (Client currentClient : clients) {
                expireClientCart(currentClient.getId());
            }
        }
    }

}
