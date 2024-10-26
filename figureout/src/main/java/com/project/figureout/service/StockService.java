package com.project.figureout.service;

import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Stock> getAllProductsInStock() {
        return stockRepository.findAll();
    }

    public Stock getProductInStockById(long id) {
        return stockRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Produto não encontrado no estoque com base no ID."));
    }

    public Stock getProductInStockByProductId(long id) {
        return stockRepository.findByProductId(id);
    }

    public void deleteProductInStockById(long id) {
        stockRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void dropStockAmountByProductId(long id, int amount) {
        Stock stock = getProductInStockById(id);

        stock.setProductQuantityAvailable(stock.getProductQuantityAvailable() - amount);
    }

    public void saveProductInStock(Stock stock) {
        stockRepository.save(stock);
    }

    public void productInStockDataSetter(Stock stock, Product product, ProductDTO productDTO) {

        stock.setSupplier(supplierRepository.findById(productDTO.getSupplier()).orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado.")));
        stock.setProductQuantityAvailable(productDTO.getStockDTO().getProductQuantityAvailable());
        stock.setProductPurchaseAmount(product.getPurchaseAmount());

        stock.setInitialEntryDate(productDTO.getStockDTO().getEntryInStockDate());

        stock.setProduct(product);

        product.getStocks().add(stock);

    }

    public void dropInStock(Stock stock, int quantity) {

        stock.setProductQuantityAvailable(stock.getProductQuantityAvailable() - quantity);
        saveProductInStock(stock);

    }

    public void dropInStockList(HashMap<Stock, Integer> stockProductsQuantityDrop) {

        for (Map.Entry<Stock, Integer> entry : stockProductsQuantityDrop.entrySet()) {
            Stock key = entry.getKey();
            Integer value = entry.getValue();

            dropInStock(key, value);

        }

    }

    public void addInStock(Stock stock, int quantity) {
        stock.setProductQuantityAvailable(stock.getProductQuantityAvailable() + quantity);
    }

    public void addInStockList(HashMap<Stock, Integer> stockProductsQuantityAdd) {

        for (Map.Entry<Stock, Integer> entry : stockProductsQuantityAdd.entrySet()) {
            Stock key = entry.getKey();
            Integer value = entry.getValue();

            addInStock(key, value);

        }

    }


}
