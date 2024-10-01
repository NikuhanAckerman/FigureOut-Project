package com.project.figureout.controller;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public String showSalesGet(Model model) {

        List<Sale> sales =  saleService.getAllSales();
        model.addAttribute("sales", sales);

        return "adminSalesView";
    }

    @GetMapping("/makeOrder/{clientId}")
    public String makeOrderGet(@PathVariable long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        Client client = cartService.getClientByCart(cart);
        List<Address> addressClientList = client.getAddresses();
        List<CreditCard> creditCardClientList = client.getCreditCards();
        ChangeCartProductQuantityDTO changeCartProductQuantityDTO = new ChangeCartProductQuantityDTO();
        PromotionalCouponDTO promotionalCouponDTO = new PromotionalCouponDTO();

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, BigDecimal> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getPriceToPay().multiply(BigDecimal.valueOf(cartsProducts.getProductQuantity())));
        }

        model.addAttribute("saleDTO", new SaleDTO());
        model.addAttribute("clientId", client.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartProductTotalPrices", cartProductTotalPrices);
        model.addAttribute("addressClientList", addressClientList);
        model.addAttribute("creditCardClientList", creditCardClientList);
        model.addAttribute("changeCartProductQuantityDTO", changeCartProductQuantityDTO);
        model.addAttribute("promotionalCouponDTO", promotionalCouponDTO);
        model.addAttribute("orderTotalPrice", cart.getTotalPrice());

        return "makeOrder";
    }

    @PostMapping("/makeOrder/{cartId}")
    public String makeOrderPost(@PathVariable long cartId, @ModelAttribute SaleDTO saleDTO, Model model) {
        Cart cart = cartService.getCartById(cartId);
        Address deliveryAddress = addressService.getAddressById(saleDTO.getDeliveryAddressId());
        List<SalesCards> salesCardsList = new ArrayList<>();

        for(long creditCardId: saleDTO.getSalesCardsIds()) {
            SalesCards currentSaleCard = new SalesCards();
            currentSaleCard.setCreditCard(creditCardService.getCreditCardById(creditCardId));
            salesCardsList.add(currentSaleCard);
        }

        if(salesCardsList.size() == 1) {
            salesCardsList.getFirst().setAmountPaid(cart.getTotalPrice());
        }

        model.addAttribute("saleCart", cart);
        model.addAttribute("chosenCreditCards", salesCardsList);
        model.addAttribute("deliveryAddress", deliveryAddress);
        model.addAttribute("orderTotalPrice", cart.getTotalPrice());

        return "finishOrder";
    }

    @PutMapping("/addPromotionalCoupon/{clientId}")
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

    @PostMapping("/finishOrder/{clientId}")
    public String createSale(@PathVariable long clientId) {


        return "shop";
    }




}
