package com.project.figureout.controller;

import com.project.figureout.dto.ExchangeDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ExchangeService;
import com.project.figureout.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    SaleService saleService;

    @Autowired
    ClientService clientService;

    @Autowired
    CartService cartService;

    @Autowired
    CartsProductsRepository cartsProductsRepository;

    @Autowired
    ExchangeService exchangeService;
    private com.project.figureout.repository.ExchangeProducts exchangeProducts;

    @GetMapping("/requestExchange/{id}")
    public String requestExchangeGet(@PathVariable long id, Model model) {
        Sale sale = saleService.getSaleById(id);
        Client client = sale.getCart().getClient();

        model.addAttribute("sale", sale);
        model.addAttribute("clientId", client.getId());
        model.addAttribute("exchangeDTO", new ExchangeDTO());

        return "requestExchange";
    }

    @PostMapping("/requestExchange/{id}")
    public String requestExchangePost(@PathVariable long id, @ModelAttribute ExchangeDTO exchangeDTO, Model model) {
        Sale sale = saleService.getSaleById(id);
        Client client = sale.getCart().getClient();
        List<Sale> clientSales = saleService.getClientSalesByClientId(client.getId());
        SaleStatusEnum status = sale.getStatus();
        SaleStatusEnum deliveredStatus = SaleStatusEnum.ENTREGUE;
        SaleStatusEnum exchangeEndedStatus = SaleStatusEnum.TROCA_FINALIZADA;

        boolean canRequestExchange = true;

        for(Exchange currentExchange: sale.getExchangeList()) {

            if(currentExchange.isCurrentExchange()) {
                canRequestExchange = false;
                break;
            }

        }

        if(canRequestExchange) {

            if(status == deliveredStatus || status == exchangeEndedStatus) {
                sale.setStatus(SaleStatusEnum.TROCA_SOLICITADA);
            } else {
                return "redirect:/clientProfilePurchases/" + client.getId();
            }

            HashMap<Long, Integer> exchangeMap = exchangeDTO.getCartProductQuantity();

            Exchange newExchange = new Exchange();
            newExchange.setSale(sale);
            exchangeService.saveExchange(newExchange);

            exchangeMap.forEach((cartProductId, quantity) -> {
                ExchangeProducts exchangeProduct = new ExchangeProducts();
                exchangeProduct.setCartProduct(cartsProductsRepository.findById(cartProductId).orElseThrow(() -> new NoSuchElementException("Produto de carrinho não encontrado.")));
                exchangeProduct.setQuantityReturned(quantity);
                exchangeProduct.setExchange(newExchange);

                newExchange.getReturnedProducts().add(exchangeProduct);
            });

            newExchange.setExchangeRequestTime(LocalDateTime.now());
            newExchange.setCurrentExchange(true);

            exchangeService.saveExchange(newExchange);

            sale.setStatus(SaleStatusEnum.TROCA_SOLICITADA);

            sale.getExchangeList().add(newExchange);
            saleService.saveSale(sale);

        }

        return "requestExchange";
    }






}
