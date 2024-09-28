package com.project.figureout.controller;

import com.project.figureout.model.*;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import com.project.figureout.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

        model.addAttribute("cart", cart);
        model.addAttribute("addressClientList", addressClientList);
        model.addAttribute("creditCardClientList", creditCardClientList);

        return "makeOrder";
    }

}
