package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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

    @Autowired
    private PromotionalCouponRepository promotionalCouponRepository;

    private ClientNavigator clientNavigator;

    @GetMapping("/getCart/{cartId}")
    public Cart getSpecificCart(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/{cartId}")
    public Cart showCart(@PathVariable Long id, Model model) {
        Cart cart = cartService.getCartById(id);

        model.addAttribute("cart", cart);

        return cart;
    }

    @PostMapping("/addProductToCart/{productId}/{cartId}")
    public String addProductToCart(@PathVariable Long productId, @PathVariable Long cartId,
                                   @ModelAttribute ChangeCartProductQuantityDTO changeCartProductQuantityDTO, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartById(cartId);

        cartService.addProductToCart(cart, product, changeCartProductQuantityDTO);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }

    @DeleteMapping("/removeProductFromCart/{productId}/{cartId}")
    public String removeProductFromCart(@PathVariable Long productId, @PathVariable Long cartId, HttpServletRequest request) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartById(cartId);

        cartService.deleteProductFromCart(cart, product);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }

    @PutMapping("/changeProductQuantity/{productId}/{cartId}")
    public String changeProductQuantity(@PathVariable Long productId, @PathVariable Long cartId,
                                        @ModelAttribute ChangeCartProductQuantityDTO changeCartProductQuantityDTO, HttpServletRequest request) {

        System.out.println("This is running!! wow!");
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartById(cartId);

        for(CartsProducts cartsProducts: cart.getCartProducts()) {

            if(cartsProducts.getProduct().getId() == product.getId()) {

                cartsProducts.setProductQuantity(changeCartProductQuantityDTO.getQuantity());

            }

        }

        cartService.setCartTotal(cart);

        // Get the previous page URL from the Referer header
        String referer = request.getHeader("Referer");

        // Redirect back to the same page
        return "redirect:" + referer;
    }

    @PutMapping("/addPromotionalCoupon/{cartId}")
    public String addPromotionalCoupon(@PathVariable long cartId, @ModelAttribute PromotionalCouponDTO promotionalCouponDTO, HttpServletRequest request) {
        Cart cart = cartService.getCartById(cartId);

        /* extremely nested code, basically what it does is if the coupon typed is correct,
         i reset the price of the products back to the original price * quantity,
         then apply the new coupon's discount
         (will potentially write it better when i transfer this to CartService and/or SaleService) */

        for(PromotionalCoupon promotionalCoupon: promotionalCouponRepository.findAll()) {

            if(promotionalCouponDTO.getCouponName().equals(promotionalCoupon.getCouponName())) {

                cartService.applyPromotionalCoupon(cart, promotionalCoupon);

            }

        }

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

}
