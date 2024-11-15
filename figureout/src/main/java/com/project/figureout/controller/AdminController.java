package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.ChangeClientNavigatorDTO;
import com.project.figureout.dto.ProductInChartDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    ClientService clientService;

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

        long amountOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        ChronoUnit groupingLogic = amountOfDaysBetween <= 30 ? ChronoUnit.DAYS :
                amountOfDaysBetween <= 365 ? ChronoUnit.MONTHS : ChronoUnit.YEARS;

        for(Sale sale: salesInsideRange) {

            for (CartsProducts cartProduct: sale.getCart().getCartProducts()) {
                String productName = cartProduct.getProduct().getName();
                BigDecimal productPrice = cartProduct.getFinalPrice();
                int productQuantity = cartProduct.getProductQuantity();

                if(!productInChartDTOList.stream().anyMatch(dto -> dto.getName().equals(productName))) {
                    System.out.println("the product hasnt been set yet! add the fakers!");

                    // Configura as vendas "falsas".
                    for(LocalDateTime currentDate = startDate; currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {

                        ProductInChartDTO productInChartDTOFake = new ProductInChartDTO();
                        productInChartDTOFake.setName(productName);
                        productInChartDTOFake.setValuePurchased(BigDecimal.valueOf(0));
                        productInChartDTOFake.setVolumePurchased(0);
                        productInChartDTOFake.setDatePurchased(currentDate);
                        productInChartDTOList.add(productInChartDTOFake);

                    }

                }

                ProductInChartDTO productInChartDTO = new ProductInChartDTO();
                productInChartDTO.setName(productName);
                productInChartDTO.setValuePurchased(cartProduct.getFinalPrice());
                productInChartDTO.setVolumePurchased(cartProduct.getProductQuantity());
                productInChartDTO.setDatePurchased(sale.getDateTimeSale());
                productInChartDTOList.add(productInChartDTO);

            }

        }

        model.addAttribute("productInChartDTOList", productInChartDTOList);

        return "chart";
    }



}
