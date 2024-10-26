package com.project.figureout.service;

import com.project.figureout.dto.ChangeSaleStatusDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.ProductRepository;
import com.project.figureout.repository.SaleRepository;
import com.project.figureout.repository.SalesCardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(long id) {
        return saleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Venda não encontrada com base no ID."));
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

    public void changeSaleStatus(Sale sale, ChangeSaleStatusDTO changeSaleStatusDTO) {
        SaleStatusEnum saleStatus = sale.getStatus();
        SaleStatusEnum changeSaleStatusDTOStatus = changeSaleStatusDTO.getStatus();
        SaleStatusEnum emProcessamento = SaleStatusEnum.EM_PROCESSAMENTO;
        SaleStatusEnum pagamentoRealizado = SaleStatusEnum.PAGAMENTO_REALIZADO;
        SaleStatusEnum trocaAutorizada = SaleStatusEnum.TROCA_AUTORIZADA;
        SaleStatusEnum trocaRecebida = SaleStatusEnum.TROCA_RECEBIDA;

        List<CartsProducts> saleCartsProducts = sale.getCart().getCartProducts();

        if(saleStatus.equals(emProcessamento)) {

            if(changeSaleStatusDTOStatus.equals(pagamentoRealizado)) {
                HashMap<Stock, Integer> cartProductQuantityToDrop = new HashMap<>();

                for(CartsProducts cartProduct : saleCartsProducts) {
                    Stock stock = stockService.getProductInStockByProductId(cartProduct.getProduct().getId());

                    cartProductQuantityToDrop.put(stock, cartProduct.getProductQuantity());

                    if(cartProduct.getProductQuantity() >= stock.getProductQuantityAvailable()) {
                        productService.inactivateProduct(stock.getProduct());
                    }
                }

                stockService.dropInStockList(cartProductQuantityToDrop);

            }

        }

        if(saleStatus.equals(trocaAutorizada)) {

            if(changeSaleStatusDTOStatus.equals(trocaRecebida)) {

                Exchange exchangeInProcess = null;

                for(Exchange currentExchange: sale.getExchangeList()) {

                    if(currentExchange.isCurrentExchange()) {
                        exchangeInProcess = currentExchange;
                        break;
                    }

                }

                List<ExchangeProducts> exchangeProducts = exchangeInProcess.getReturnedProducts();

                HashMap<Stock, Integer> cartProductQuantityToAdd = new HashMap<>();

                for(CartsProducts cartProduct : saleCartsProducts) {
                    Stock stock = stockService.getProductInStockByProductId(cartProduct.getProduct().getId());

                    for(ExchangeProducts currentExchangeProduct : exchangeProducts) {

                        if(currentExchangeProduct.getCartProduct().getId() == cartProduct.getId()) {

                            cartProductQuantityToAdd.put(stock, currentExchangeProduct.getQuantityReturned());

                        }

                    }

                }

                stockService.addInStockList(cartProductQuantityToAdd);

                exchangeService.generateExchangeCoupon(exchangeInProcess);

            }

        }

        sale.setStatus(changeSaleStatusDTOStatus);

        saveSale(sale);

    }


}
