package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import com.project.figureout.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private StockService stockService;

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
                                   @ModelAttribute ChangeCartProductQuantityDTO changeCartProductQuantityDTO, HttpServletRequest request,
                                   Model model) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartById(cartId);

        int quantityOrdered = changeCartProductQuantityDTO.getQuantity();
        int availableQuantity = stockService.getProductInStockByProductId(productId).getProductQuantityAvailable();

        List<String> errors = new ArrayList<>();

        if(quantityOrdered > availableQuantity) {
            errors.add("Quantidade indisponível em estoque.");
        }

        if(quantityOrdered <= 0) {
            errors.add("Não se pode pedir uma quantidade de 0.");
        }

        boolean isAlreadyInCart = false;
        for(CartsProducts cartProduct: cart.getCartProducts()) {

            if(cartProduct.getProduct().getId() == productId) {
                isAlreadyInCart = true;
            }

        }

        if(isAlreadyInCart) {
            errors.add("Não se pode adicionar o mesmo produto no carrinho múltiplas vezes.");
        }

        if(!errors.isEmpty()) {
            Stock stock = stockService.getProductInStockByProductId(productId);
            List<Category> productCategoryList = product.getCategories();
            Client client = clientService.getClientById(clientNavigator.getInstance().getClientId());
            model.addAttribute("errors", errors);
            model.addAttribute("stock", stock);
            model.addAttribute("changeCartProductQuantityDTO", new ChangeCartProductQuantityDTO());
            model.addAttribute("product", product);
            model.addAttribute("cart", client.getCartList().getLast());

            return "product";
        }

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
                                        @ModelAttribute ChangeCartProductQuantityDTO changeCartProductQuantityDTO, HttpServletRequest request,
                                        Model model) {

        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartById(cartId);
        String referer = request.getHeader("Referer");

        int quantityOrdered = changeCartProductQuantityDTO.getQuantity();
        int availableQuantity = stockService.getProductInStockByProductId(productId).getProductQuantityAvailable();

        List<String> errors = new ArrayList<>();

        if(quantityOrdered > availableQuantity) {
            errors.add("Quantidade indisponível em estoque.");
        }

        if(quantityOrdered == 0) {
            errors.add("Não se pode pedir uma quantidade de 0.");
        }

        if(!errors.isEmpty()) {
            model.addAttribute("errors", errors);

            return "redirect:" + referer;
        }

        for(CartsProducts cartsProducts: cart.getCartProducts()) {

            if(cartsProducts.getProduct().getId() == product.getId()) {

                cartService.changeProductQuantity(cartsProducts, changeCartProductQuantityDTO);

            }

        }

        cartService.setCartTotal(cart);

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

    @DeleteMapping("/removePromotionalCoupon/{cartId}")
    public String removePromotionalCoupon(@PathVariable long cartId, HttpServletRequest request) {
        Cart cart = cartService.getCartById(cartId);

        cartService.removePromotionalCoupon(cart);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

}
