package com.project.figureout.controller;

import com.project.figureout.dto.CartProductDTO;
import com.project.figureout.dto.ChangeCartProductQuantityDTO;
import com.project.figureout.dto.MultipleCartProductDTO;
import com.project.figureout.dto.PromotionalCouponDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import com.project.figureout.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private PromotionalCouponRepository promotionalCouponRepository;

    @GetMapping("")
    public String showSalesGet(Model model) {

        List<Sale> sales =  saleService.getAllSales();
        model.addAttribute("sales", sales);

        return "adminSalesView";
    }

    @GetMapping("/makeOrder/{clientId}")
    public String makeOrderGet(@PathVariable long clientId, Model model) {
        Client client = clientService.getClientById(clientId);
        Cart cart = cartService.getCartByClientId(clientId);
        List<Address> addressClientList = client.getAddresses();
        List<CreditCard> creditCardClientList = client.getCreditCards();
        ChangeCartProductQuantityDTO changeCartProductQuantityDTO = new ChangeCartProductQuantityDTO();
        PromotionalCouponDTO promotionalCouponDTO = new PromotionalCouponDTO();

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, Double> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getProduct().getPrice() * cartsProducts.getProductQuantity());
        }

        model.addAttribute("clientId", clientId);
        model.addAttribute("cart", cart);
        model.addAttribute("cartProductTotalPrices", cartProductTotalPrices);
        model.addAttribute("addressClientList", addressClientList);
        model.addAttribute("creditCardClientList", creditCardClientList);
        model.addAttribute("changeCartProductQuantityDTO", changeCartProductQuantityDTO);
        model.addAttribute("promotionalCouponDTO", promotionalCouponDTO);
        model.addAttribute("orderTotalPrice", cart.getTotalPrice());

        return "makeOrder";
    }

    @PutMapping("/addPromotionalCoupon/{clientId}")
    public String addPromotionalCoupon(@PathVariable long clientId, @ModelAttribute PromotionalCouponDTO promotionalCouponDTO, HttpServletRequest request) {
        Cart cart = cartService.getCartByClientId(clientId);

        /* extremely nested code, basically what it does is if the coupon typed is correct,
         i reset the price of the products back to the original price * quantity,
         then apply the new coupon's discount
         (will potentially write it better when i transfer this to CartService and/or SaleService) */

        for(PromotionalCoupon promotionalCoupon: promotionalCouponRepository.findAll()) {

            if(promotionalCouponDTO.getCouponName().equals(promotionalCoupon.getCouponName())) {
                System.out.println("wow, couponname is equal to the promotional coupon!");
                for(CartsProducts cartsProducts : cart.getCartProducts()) {

                    if(cart.getPromotionalCoupon() != null) {
                        System.out.println("sussybaka.. already applied a coupon didnt you?!");
                        cartsProducts.setPriceToPay(cartsProducts.getProduct().getPrice() * cartsProducts.getProductQuantity());

                    }
                    System.out.println("anyway, put the fries in the bag lil bro, heres a discount:");
                    cart.setPromotionalCoupon(promotionalCouponRepository.findById(promotionalCoupon.getId()).orElseThrow(() -> new NoSuchElementException("Cupom promocional não encontrado com base no ID.")));
                    cartsProducts.setPriceToPay(cartsProducts.getPriceToPay() - (cartsProducts.getPriceToPay() * (promotionalCoupon.getCouponDiscount()/100)));
                    cartService.setCartTotal(cart);
                }

            }

        }



        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }


}
