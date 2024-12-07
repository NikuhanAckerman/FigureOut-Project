package com.project.figureout.service;

import com.project.figureout.dto.ChangeSaleStatusDTO;
import com.project.figureout.dto.NotificationDTO;
import com.project.figureout.dto.SaleDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.ProductRepository;
import com.project.figureout.repository.SaleRepository;
import com.project.figureout.repository.SalesCardsRepository;
import jakarta.transaction.Transactional;
import org.hibernate.loader.LoaderLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SaleService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SalesCardsRepository salesCardsRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ChatGptService chatGptService;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(long id) {
        return saleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Venda não encontrada com base no ID."));
    }

    public List<Sale> getSalesInsideDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> allSales = getAllSales();
        List<Sale> salesInsideDesiredRange = new ArrayList<>();

        for(Sale currentSale : allSales) {
            LocalDateTime currentSaleDateTime = currentSale.getDateTimeSale();

            if(currentSaleDateTime.isAfter(startDate) && currentSaleDateTime.isBefore(endDate)) {

                salesInsideDesiredRange.add(currentSale);
            }
        }
        return salesInsideDesiredRange;
    }

    public List<Sale> getClientSalesByClientId(long id) {

        List<Sale> allSales = getAllSales();
        List<Sale> clientSales = new ArrayList<>();

        for(Sale sale : allSales) {
            Cart saleCart = sale.getCart();
            long saleCartClientId = saleCart.getClient().getId();

            if(saleCartClientId == id) {
                clientSales.add(sale);
            }

        }

        return clientSales;

    }

    public List<SalesCards> getSalesCardsBySaleId(long id) {

        return salesCardsRepository.findSalesCardsBySaleId(id);
    }

    public void deleteSaleById(long id) {
        saleRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

//    public List<SaleDTO> getSalesByProductByMonth() {
//        return saleRepository.findSalesByProductByMonth();
//    }

    public void changeSaleStatus(Sale sale, ChangeSaleStatusDTO changeSaleStatusDTO) throws IOException {
        SaleStatusEnum saleStatus = sale.getStatus();
        SaleStatusEnum changeSaleStatusDTOStatus = changeSaleStatusDTO.getStatus();
        SaleStatusEnum emProcessamento = SaleStatusEnum.EM_PROCESSAMENTO;
        SaleStatusEnum pagamentoRealizado = SaleStatusEnum.PAGAMENTO_REALIZADO;
        SaleStatusEnum trocaSolicitada = SaleStatusEnum.TROCA_SOLICITADA;
        SaleStatusEnum trocaNaoAutorizada = SaleStatusEnum.TROCA_NAO_AUTORIZADA;
        SaleStatusEnum trocaAutorizada = SaleStatusEnum.TROCA_AUTORIZADA;
        SaleStatusEnum emTroca = SaleStatusEnum.EM_TROCA;
        SaleStatusEnum trocaRecebida = SaleStatusEnum.TROCA_RECEBIDA;
        SaleStatusEnum trocaFinalizada = SaleStatusEnum.TROCA_FINALIZADA;
        List<Enum> exchangeEnumerations = new ArrayList<>();
        exchangeEnumerations.add(trocaSolicitada);
        exchangeEnumerations.add(trocaNaoAutorizada);
        exchangeEnumerations.add(trocaAutorizada);
        exchangeEnumerations.add(emTroca);
        exchangeEnumerations.add(trocaRecebida);
        exchangeEnumerations.add(trocaFinalizada);


        List<CartsProducts> saleCartsProducts = sale.getCart().getCartProducts();

        Exchange exchangeInProcessFirst = null;

        for(Exchange currentExchange: sale.getExchangeList()) {

            if(currentExchange.isCurrentExchange()) {

                exchangeInProcessFirst = currentExchange;
                break;
            }

        }

        LocalDateTime now = LocalDateTime.now();

        for(ExchangeStatusEnum currentEnum: ExchangeStatusEnum.values()) {

            if(changeSaleStatusDTOStatus.name().equals(currentEnum.name())) {

                if(exchangeInProcessFirst == null) {
                    for(Enum enumer: exchangeEnumerations) {
                        if(changeSaleStatusDTOStatus.equals(enumer)) {
                            return;
                        }
                    }

                    sale.setStatus(changeSaleStatusDTOStatus);

                    NotificationDTO notificationDTO = new NotificationDTO();
                    notificationDTO.setCategory(NotificationCategoryEnum.VENDA);
                    notificationDTO.setTitle("O status da venda de código '" + sale.getSaleCode() + "' foi alterado.");
                    notificationDTO.setDescription("O status desta venda foi alterado de " + sale.getStatus().name() + " para " + changeSaleStatusDTO.getStatus().name() + ".");
                    notificationService.createNotification(sale.getCart().getClient(), notificationDTO);
                    return;
                }

                exchangeInProcessFirst.setStatus(currentEnum);

                if(currentEnum.equals(trocaAutorizada)) {
                    exchangeInProcessFirst.setExchangeAcceptedTime(now);
                }

                if(currentEnum.equals(trocaFinalizada)) {
                    exchangeInProcessFirst.setExchangeFinishTime(now);
                }

                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setCategory(NotificationCategoryEnum.TROCA);
                notificationDTO.setTitle("O status da troca de código '" + exchangeInProcessFirst.getExchangeCode() + "' foi alterado.");
                notificationDTO.setDescription("O status desta troca foi alterado de " + exchangeInProcessFirst.getStatus().name() + " para " + changeSaleStatusDTO.getStatus().name() + ".");
                notificationService.createNotification(sale.getCart().getClient(), notificationDTO);

            }

        }

        if(changeSaleStatusDTOStatus.equals(trocaFinalizada) || changeSaleStatusDTOStatus.equals(trocaNaoAutorizada)) {

            if(changeSaleStatusDTOStatus.equals(trocaNaoAutorizada)) {

                if(exchangeInProcessFirst != null) {

                    for(ExchangeProducts currentProduct: exchangeInProcessFirst.getReturnedProducts()) {

                        currentProduct.setFinalAmount(BigDecimal.valueOf(0.00));
                        currentProduct.setQuantityReturned(0);

                    }

                    exchangeInProcessFirst.setCurrentExchange(false);

                }

            }

            if(exchangeInProcessFirst != null) {

                exchangeInProcessFirst.setCurrentExchange(false);

            }

        }

        if(saleStatus.equals(emTroca)) {

            if(changeSaleStatusDTOStatus.equals(trocaRecebida)) {

                Exchange exchangeInProcess = null;

                for(Exchange currentExchange: sale.getExchangeList()) {

                    if(currentExchange.isCurrentExchange()) {
                        exchangeInProcess = currentExchange;
                        break;
                    }

                }

                if(exchangeInProcess == null) {
                    for(Enum enumer: exchangeEnumerations) {
                        if(changeSaleStatusDTOStatus.equals(enumer)) {
                            return;
                        }
                    }

                    sale.setStatus(changeSaleStatusDTOStatus);
                    return;
                }

                List<ExchangeProducts> exchangeProducts = exchangeInProcess.getReturnedProducts();

                HashMap<Stock, Integer> cartProductQuantityToAdd = new HashMap<>();

                for(CartsProducts cartProduct : saleCartsProducts) {
                    Stock stock = stockService.getProductInStockByProductId(cartProduct.getProduct().getId());

                    for(ExchangeProducts currentExchangeProduct : exchangeProducts) {

                        if(currentExchangeProduct.getCartProduct().getId() == cartProduct.getId()) {

                            cartProductQuantityToAdd.put(stock, currentExchangeProduct.getQuantityReturned());
                            cartProduct.setExchangeableQuantity(cartProduct.getExchangeableQuantity() - currentExchangeProduct.getQuantityReturned());

                        }

                    }

                }

                stockService.addInStockList(cartProductQuantityToAdd);

                exchangeService.generateExchangeCoupon(exchangeInProcess);

            }

        }

        // creating notification
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setCategory(NotificationCategoryEnum.VENDA);
        notificationDTO.setTitle("O status da venda de código '" + sale.getSaleCode() + "' foi alterado.");
        notificationDTO.setDescription("O status desta venda foi alterado de " + sale.getStatus().name() + " para " + changeSaleStatusDTO.getStatus().name() + ".");
        notificationService.createNotification(sale.getCart().getClient(), notificationDTO);

        sale.setStatus(changeSaleStatusDTOStatus);

        saveSale(sale);

    }

    public List<Sale> findSalesByDate(LocalDateTime date) {
        return saleRepository.findByDateTimeSale(date);
    }


}
