package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.ChangeClientNavigatorDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.Log;
import com.project.figureout.repository.LogRepository;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    ClientService clientService;

    @Autowired
    private LogService logService;

    @Autowired
    private LogRepository logRepository;

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

    private ClientNavigator clientNavigator;

    @GetMapping({"","/","/index"})
    public String getControlPanel(Model model) {
        model.addAttribute("clientList", clientService.getAllClients());
        model.addAttribute("changeClientNavigatorDTO", new ChangeClientNavigatorDTO());

        model.addAttribute("isClientIdNull", false);
        List<Log> logs = logRepository.findAll();
        model.addAttribute("logs", logs);

        if(clientNavigator.getInstance().getClientId() == 0) {
            System.out.println("é zero lol");
            model.addAttribute("isClientIdNull", true);
        } else {
            Client client = clientService.getClientById(clientNavigator.getInstance().getClientId());
            model.addAttribute("currentClientNavigator", client);
        }

        return "adminControlPanel";
    }

    @PutMapping("/changeClientNavigator/")
    public String changeClientNavigator(Model model, @ModelAttribute ChangeClientNavigatorDTO changeClientNavigatorDTO) {

        clientNavigator.getInstance().setClientId(changeClientNavigatorDTO.getClientId());

        System.out.println(clientNavigator.getInstance().getClientId());

        return "redirect:/index";
    }

}
