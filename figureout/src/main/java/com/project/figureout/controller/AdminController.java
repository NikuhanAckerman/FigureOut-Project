package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.ChangeClientNavigatorDTO;
import com.project.figureout.dto.ProductInChartDTO;
import com.project.figureout.dto.SaleDTO;
import com.project.figureout.model.CartsProducts;
import com.project.figureout.model.Client;
import com.project.figureout.model.Log;
import com.project.figureout.model.Sale;
import com.project.figureout.repository.LogRepository;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    SaleService saleService;

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

    @GetMapping("/statistics")
    public String getStatistics() {
        return "statistics";
    }

    @GetMapping("/statistics/clientRanking/")
    public String getClientRanking(Model model) {
        List<Client> allClients = clientService.getAllClients();

        allClients.sort(Comparator.comparingInt(Client::getRanking));

        model.addAttribute("clients", allClients);

        return "clientRanking";
    }

//    @GetMapping("/chart/changeDateInterval")
//    public HashMap<LocalDateTime, BigDecimal> changeDateInterval(Model model) {
//    // Comentei pois dá erro de compilação por não ter um return.
//    }


    @GetMapping("/chart")
    public String chart(Model model, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        List<Sale> salesInsideRange = saleService.getSalesInsideDateRange(startDate, endDate);
        List<ProductInChartDTO> productInChartDTOList = new ArrayList<>();

        for(Sale currentSale: salesInsideRange) {

            for(CartsProducts cartProduct: currentSale.getCart().getCartProducts()) {

                if(productInChartDTOList.stream().anyMatch(productInChartDTO -> productInChartDTO.equals(cartProduct.getProduct().getId()))) {

                    






                } else {
                    ProductInChartDTO productInChartDTO = new ProductInChartDTO();
                    productInChartDTO.setProductId(cartProduct.getProduct().getId());
                    productInChartDTO.setName(cartProduct.getProduct().getName());
                    productInChartDTO.setValuePurchased(cartProduct.getFinalPrice());
                }





            }

        }


        return "chart"; // nome do template Thymeleaf
    }



}
