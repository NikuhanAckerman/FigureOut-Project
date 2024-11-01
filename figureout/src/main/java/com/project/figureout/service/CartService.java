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
    private ExchangeService exchangeService;

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
        cartProduct.setUnitaryPrice(product.getPrice()); // setting a temporary price here so it doesnt come out as 0 when i check for promotional coupon
        cartProduct.setExchangeableQuantity(changeCartProductQuantityDTO.getQuantity());

        if(cart.getPromotionalCoupon() != null) {
            setPromotionalCouponDiscount(cartProduct, cart.getPromotionalCoupon());
        } else {
            cartProduct.setUnitaryPrice(product.getPrice());
            cartProduct.setFinalPrice(cartProduct.getUnitaryPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
        }

        LocalDateTime now = LocalDateTime.now();
        cartProduct.setProductAddedTime(now);

        cart.getCartProducts().add(cartProduct);

        setCartTotal(cart);
    }

    public void changeProductQuantity(CartsProducts cartProduct, ChangeCartProductQuantityDTO changeCartProductQuantityDTO) {
        cartProduct.setProductQuantity(changeCartProductQuantityDTO.getQuantity());
        cartProduct.setExchangeableQuantity(changeCartProductQuantityDTO.getQuantity());
        cartProduct.setFinalPrice(cartProduct.getUnitaryPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
    }

    public void setPromotionalCouponDiscount(CartsProducts cartProduct, PromotionalCoupon promotionalCoupon) {
        BigDecimal cartProductOldPrice = cartProduct.getProduct().getPrice();

        BigDecimal newPrice = cartProductOldPrice.subtract(cartProductOldPrice.multiply(promotionalCoupon.getCouponDiscount()));

        cartProduct.setUnitaryPrice(newPrice);
        cartProduct.setFinalPrice(newPrice.multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
    }

    public void applyPromotionalCoupon(Cart cart, PromotionalCoupon promotionalCoupon) {
        List<CartsProducts> cartsProducts = cart.getCartProducts();

        if(cart.getPromotionalCoupon() != null) {
            if(cart.getPromotionalCoupon().equals(promotionalCoupon)) {
                return;
            }

            for(CartsProducts cartProduct: cartsProducts) {
                System.out.println(cartProduct.getUnitaryPrice());
                cartProduct.setUnitaryPrice(cartProduct.getProduct().getPrice());
                cartProduct.setFinalPrice(cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
                System.out.println(cartProduct.getFinalPrice());
            }
        }

        cart.setPromotionalCoupon(promotionalCoupon);

        for(CartsProducts cartProduct: cartsProducts) {
            setPromotionalCouponDiscount(cartProduct, promotionalCoupon);
        }

        setCartTotal(cart);
    }

    public void removePromotionalCoupon(Cart cart) {
        List<CartsProducts> cartsProducts = cart.getCartProducts();

        if(cart.getPromotionalCoupon() != null) {

            for(CartsProducts cartProduct: cartsProducts) {
                cartProduct.setUnitaryPrice(cartProduct.getProduct().getPrice());
                cartProduct.setFinalPrice(cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
            }

        }

        cart.setPromotionalCoupon(null);

        setCartTotal(cart);

    }

    public void applyExchangeCoupon(Cart cart, ExchangeCoupon exchangeCoupon) {
        List<CartsProducts> cartsProducts = cart.getCartProducts();

        if(cart.getExchangeCoupons().contains(exchangeCoupon)) {
            return;
        }

        cart.getExchangeCoupons().add(exchangeCoupon);

        BigDecimal difference;
        difference = exchangeCoupon.getAmountWorth();

        for(CartsProducts cartProduct: cartsProducts) {

            BigDecimal cartProductTotal = cartProduct.getFinalPrice();
            System.out.println("CartProduct total price: " + cartProductTotal);
            System.out.println("Difference currently: " + difference);
            difference = difference.subtract(cartProductTotal);
            System.out.println("Difference after subtraction currently: " + difference);
        }

        System.out.println("Valor de sobra do cupom de troca " + exchangeCoupon.getExchangeCouponCode() + " : " + difference);

        if(difference.compareTo(BigDecimal.valueOf(0)) == 0) {
            System.out.println("Difference is equal to 0");

            for(CartsProducts cartProduct: cartsProducts) {
                cartProduct.setUnitaryPrice(BigDecimal.valueOf(0));
                cartProduct.setFinalPrice(BigDecimal.valueOf(0));
            }

        } else if(difference.compareTo(BigDecimal.valueOf(0)) == -1) {
            System.out.println("Difference is lesser than 0");

            difference = difference.abs();

            for(CartsProducts cartProduct: cartsProducts.reversed()) {
                cartProduct.setUnitaryPrice(difference.divide(BigDecimal.valueOf(cartProduct.getProductQuantity())));
                cartProduct.setFinalPrice(cartProduct.getFinalPrice().subtract(difference));
            }

        } else if(difference.compareTo(BigDecimal.valueOf(0)) == 1) {
            System.out.println("Difference is bigger than 0");

            BigDecimal total = cart.getTotalPrice();

            for(CartsProducts cartProduct: cartsProducts) {
                cartProduct.setUnitaryPrice(BigDecimal.valueOf(0));
                cartProduct.setFinalPrice(BigDecimal.valueOf(0));
            }

            BigDecimal percentile = exchangeService.getPercentileToCreateNewExchangeCoupon();
            BigDecimal totalPercentile = total.multiply(percentile);

            if(difference.compareTo(totalPercentile) >= 0) {
                exchangeService.generateExchangeCouponSurpass(cart.getClient(), difference);
            }

        }

        setCartTotal(cart);
    }

    public void removeExchangeCoupon(Cart cart, ExchangeCoupon exchangeCoupon) {
        List<CartsProducts> cartsProducts = cart.getCartProducts();
        BigDecimal couponAmount = exchangeCoupon.getAmountWorth();
        BigDecimal amountLeftover = couponAmount;

        for(CartsProducts cartProduct: cartsProducts.reversed()) {

            BigDecimal finalAmountNeeded = cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity()));

            amountLeftover = amountLeftover.subtract(finalAmountNeeded);

            if(amountLeftover.compareTo(BigDecimal.valueOf(0)) == -1) {

                cartProduct.setFinalPrice(amountLeftover);
                cartProduct.setUnitaryPrice(amountLeftover.divide(BigDecimal.valueOf(cartProduct.getProductQuantity())));

            } else if (amountLeftover.compareTo(BigDecimal.valueOf(0)) >= 0) {

                cartProduct.setFinalPrice(cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getProductQuantity())));
                cartProduct.setUnitaryPrice(cartProduct.getProduct().getPrice());

            }

            amountLeftover = amountLeftover.subtract(cartProduct.getFinalPrice());
        }

        if(cart.getPromotionalCoupon() != null) {
            applyPromotionalCoupon(cart, cart.getPromotionalCoupon());
        }

        cart.getExchangeCoupons().remove(exchangeCoupon);

        setCartTotal(cart);
    }

    public void setCartTotal(Cart cart) {
        BigDecimal total = new BigDecimal(0);
        System.out.println("calling setCartTotal");

        // Get the list of products in the cart
        List<CartsProducts> cartProducts = cart.getCartProducts();

        // Loop through each product in the cart and calculate the total price
        for (CartsProducts cartsProduct : cartProducts) {
            BigDecimal productTotal = cartsProduct.getFinalPrice();
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
                LocalDateTime expirationTime = currentCart.getDateOfCreation().plusMinutes(20);

                if (now.isAfter(expirationTime)) {
                    System.out.println("Cart expired");

                    changeClientCart(client);  // Handle expired cart
                }
            }
        }

    }

    private static final int qntMinutesExpire = 1;  // Set to 20 minutes
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
