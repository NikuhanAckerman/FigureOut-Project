package com.project.figureout.service;

import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.Category;
import com.project.figureout.model.PricingGroup;
import com.project.figureout.model.Product;
import com.project.figureout.model.Stock;
import com.project.figureout.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        stock.setEntryDateTime(LocalDateTime.now());
        stock.setProduct(product);

    }


}