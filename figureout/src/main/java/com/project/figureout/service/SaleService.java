package com.project.figureout.service;

import com.project.figureout.model.Cart;
import com.project.figureout.model.Client;
import com.project.figureout.model.Sale;
import com.project.figureout.model.SalesCards;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.ProductRepository;
import com.project.figureout.repository.SaleRepository;
import com.project.figureout.repository.SalesCardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


}
