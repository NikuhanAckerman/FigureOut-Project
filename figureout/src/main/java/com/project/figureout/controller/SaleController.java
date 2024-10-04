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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/makeOrder/{cartId}")
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
    public String makeOrderPost(@PathVariable long cartId, @ModelAttribute SaleDTO saleDTO, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCartById(cartId);
        System.out.println("address in saleDTO: " + saleDTO.getDeliveryAddressId());
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

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, BigDecimal> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getPriceToPay().multiply(BigDecimal.valueOf(cartsProducts.getProductQuantity())));
        }

        System.out.println("Lista de salescards: " + salesCardsList);

        redirectAttributes.addFlashAttribute("saleCart", cart);
        redirectAttributes.addFlashAttribute("cartProductTotalPrices", cartProductTotalPrices);
        redirectAttributes.addFlashAttribute("salesCardsList", salesCardsList);
        redirectAttributes.addFlashAttribute("deliveryAddress", deliveryAddress);
        redirectAttributes.addFlashAttribute("orderTotalPrice", cart.getTotalPrice());

        return "redirect:/sales/finishOrder/" + cartId;
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

    @GetMapping("/finishOrder/{cartId}")
    public String finishOrderGet(@PathVariable long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        Client client = cartService.getClientByCart(cart);
        Address deliveryAddress = (Address) model.getAttribute("deliveryAddress");
        System.out.println(deliveryAddress.getNickname());
        List<CreditCard> creditCardClientList = client.getCreditCards();
        List<SalesCards> listSalesCards = (List<SalesCards>) model.getAttribute("salesCardsList");

        List<SaleCardDTO> saleCardDTOList = new ArrayList<>();

        for(SalesCards saleCard: listSalesCards) {
            SaleCardDTO saleCardDTO = new SaleCardDTO();
            saleCardDTO.setCardId(saleCard.getCreditCard().getId());

            saleCardDTOList.add(saleCardDTO);
        }

        model.addAttribute("saleCardDTOList", saleCardDTOList);


        return "finishOrder";
    }

    @PostMapping("/finishOrder/{cartId}")
    public String createSale(@PathVariable long cartId, @ModelAttribute SaleCardDTO saleCardDTO, Model model) {
        System.out.println("O post do finish order rodou lol");
        Cart cart = cartService.getCartById(cartId);

        System.out.println("LOOOL" + saleCardDTO.getAmountPaid());

        Sale sale = new Sale();

        sale.setCart(cart);

        Address deliveryAddress = (Address) model.getAttribute("deliveryAddress");

        sale.setDeliveryAddress(deliveryAddress);

        List<SalesCards> listSalesCards = (List<SalesCards>) model.getAttribute("salesCardsList");

        for(SalesCards saleCard: listSalesCards) {
            sale.getCardsUsedInThisSale().add(saleCard);


            saleCard.setAmountPaid(saleCardDTO.getAmountPaid());


        }

        sale.setStatus(SaleStatusEnum.PAGAMENTO_REALIZADO);

        sale.setPromotionalCouponApplied(sale.getCart().getPromotionalCoupon());

        return "redirect:/shop";
    }




}
