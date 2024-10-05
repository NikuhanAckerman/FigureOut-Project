package com.project.figureout.service;

import com.project.figureout.model.Client;
import com.project.figureout.model.Sale;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.ProductRepository;
import com.project.figureout.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(long id) {
        return saleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Venda não encontrada com base no ID."));
    }

    public void deleteSaleById(long id) {
        saleRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }


}
