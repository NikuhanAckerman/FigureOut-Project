package com.project.figureout.controller;

import com.project.figureout.dto.CartProductDTO;
import com.project.figureout.dto.StockDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CartsProductsRepository cartsProductsRepository;


    @GetMapping("/getCartByClientId/{id}")
    public Cart getSpecificCart(@PathVariable Long id) {
        return cartService.getCartByClientId(id);
    }

    @GetMapping("/{clientId}")
    @ResponseBody
    public Cart showCart(@PathVariable Long id, Model model) {
        Cart cart = cartService.getCartByClientId(id);

        model.addAttribute("cart", cart);

        return cart;
    }

    @PostMapping("/addProductToCart/{productId}/{clientId}")
    public String addProductToCart(@PathVariable Long productId, @PathVariable Long clientId,
                                   @ModelAttribute CartProductDTO cartProductDTO,
                                   Model model, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        Client client = clientService.getClientById(clientId);
        Cart cart = cartService.getCartByClientId(clientId);

        cartService.addProductToCart(cart, product, cartProductDTO);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }

    @DeleteMapping("/removeProductFromCart/{productId}/{clientId}")
    public String removeProductFromCart(@PathVariable Long productId, @PathVariable Long clientId, Model model, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartByClientId(clientId);

        cartService.deleteProductFromCart(cart, product);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }

    @PostMapping("/changeProductQuantity/{productId}/{clientId}")
    public String changeProductQuantity(@PathVariable Long productId, @PathVariable Long clientId,
                                   @ModelAttribute StockDTO stockDTO,
                                   Model model, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        Client client = clientService.getClientById(clientId);
        Cart cart = cartService.getCartByClientId(clientId);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }








}
