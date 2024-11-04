package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.ChangeClientNavigatorDTO;
import com.project.figureout.dto.ProductInChartDTO;
import com.project.figureout.dto.SaleDTO;
import com.project.figureout.model.*;
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


    @GetMapping("/chart")
    public String chart(@RequestParam(defaultValue = "2024-01-01T00:00:00") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                        @RequestParam(defaultValue = "2024-12-31T00:00:00") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                        Model model) {
        List<Sale> salesInsideRange = saleService.getSalesInsideDateRange(startDate, endDate);
        List<ProductInChartDTO> productInChartDTOList = new ArrayList<>();

        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        for(Sale sale: salesInsideRange) {

            System.out.println("");
            System.out.println("Venda");
            System.out.println("Código da venda: " + sale.getSaleCode());
            System.out.println("Valor final da venda: R$" + sale.getFinalPrice());
            System.out.println("Produtos:");

            for (CartsProducts cartProduct: sale.getCart().getCartProducts()) {
                long productId = cartProduct.getProduct().getId();
                BigDecimal productPrice = cartProduct.getFinalPrice();
                int productQuantity = cartProduct.getProductQuantity();

                ProductInChartDTO productInChartDTO = new ProductInChartDTO();
                productInChartDTO.setId(productId);
                productInChartDTO.setName(cartProduct.getProduct().getName());
                productInChartDTO.setValuePurchased(cartProduct.getFinalPrice());
                productInChartDTO.setVolumePurchased(cartProduct.getProductQuantity());
                productInChartDTO.setDatePurchased(sale.getDateTimeSale());
                productInChartDTOList.add(productInChartDTO);

                System.out.println("");
                System.out.println("    Nome: " + productInChartDTO.getName());
                System.out.println("    Valor de compra: R$" + productInChartDTO.getValuePurchased());
                System.out.println("    Volume de compra:" + productInChartDTO.getVolumePurchased());
                System.out.println("    Data específica da venda " + productInChartDTO.getDatePurchased());

            }

            System.out.println("");

        }

        System.out.println("Elementos da lista de productInChartDTO:");

        for(ProductInChartDTO productInChartDTO: productInChartDTOList) {
            System.out.println("");
            System.out.println("Nome: " + productInChartDTO.getName());
            System.out.println("Valor de compra: R$" + productInChartDTO.getValuePurchased());
            System.out.println("Volume de compra:" + productInChartDTO.getVolumePurchased());
            System.out.println("Data de venda: " + productInChartDTO.getDatePurchased());
        }

        model.addAttribute("productInChartDTOList", productInChartDTOList);

        return "chart"; // nome do template Thymeleaf
    }



}
