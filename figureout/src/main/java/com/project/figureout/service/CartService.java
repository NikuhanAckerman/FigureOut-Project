package com.project.figureout.service;

import com.project.figureout.model.Cart;
import com.project.figureout.model.Client;
import com.project.figureout.model.Product;
import com.project.figureout.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CartService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

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

    public void deleteCartById(long id) {
        cartRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}
