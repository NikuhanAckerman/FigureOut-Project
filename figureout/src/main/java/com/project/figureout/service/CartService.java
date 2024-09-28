package com.project.figureout.service;

import com.project.figureout.dto.CartProductDTO;
import com.project.figureout.dto.StockDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.CartsProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
public class CartService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartsProductsRepository cartsProductsRepository;

    public Cart getCartById(long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado com base no ID."));
    }

    public Cart getCartByClientId(long id) {

        for(Client client: clientService.getAllClients()) {
            if(client.getId() == id) {
                return getCartById(client.getId());
            }

        }

        throw new NoSuchElementException("Carrinho não encontrado com base no ID do cliente.");
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteProductFromCart(Cart cart, Product product) {
        cart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().getId() == (product.getId()));
        saveCart(cart);
    }

    public void addProductToCart(Cart cart, Product product, CartProductDTO cartProductDTO) {
        CartsProductsKey cartsProductsKey = new CartsProductsKey(cart.getId(), product.getId());

        CartsProducts cartProduct = new CartsProducts();
        cartProduct.setId(cartsProductsKey);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setProductQuantity(cartProductDTO.getProductQuantityAvailable());

        LocalDateTime now = LocalDateTime.now();
        cartProduct.setProductAddedTime(now);

        cart.getCartProducts().add(cartProduct);

        saveCart(cart);
    }

}
