package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    ClientService clientService;

    @Autowired
    AddressService addressService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    @Autowired
    PromotionalCouponRepository promotionalCouponRepository;

    ClientNavigator clientNavigator;


    @GetMapping({"","/","/index"})
    public String getControlPanel(Model model) {
        model.addAttribute("clientList", clientService.getAllClients());

        return "adminControlPanel";
    }

    @PutMapping("/changeClientNavigator/{id}")
    public String changeClientNavigator(Model model, @PathVariable long id) {
        clientNavigator.setClientId(id);

        return "redirect:/index";
    }

}
