package com.project.figureout.service;

import com.project.figureout.dto.ClientBasicDataDTO;
import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PricingGroupRepository pricingGroupRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private InactiveProductsRepository inactiveProductsRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Produto não encontrado com base no ID."));
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<PricingGroup> getAllPricingGroups() {
        return pricingGroupRepository.findAll();
    }

    public void productDataSetter(Product product, ProductDTO productDTO) {
        System.out.println("calling product data setter");
        product.setActive(productDTO.isActive());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setHeight(productDTO.getHeight());
        product.setWidth(productDTO.getWidth());
        product.setWeight(productDTO.getWeight());
        product.setLength(productDTO.getWidth());
        product.setPurchaseAmount(productDTO.getPurchaseAmount());

        List<Category> categoryListAddToProduct = new ArrayList<>();
        categoryListAddToProduct.addAll(categoryRepository.findAllById(productDTO.getCategoriesIds()));

        product.setCategories(categoryListAddToProduct);

        product.setPricingGroup(pricingGroupRepository.findById(productDTO.getPricingGroup()).orElseThrow(() -> new NoSuchElementException("Grupo de precificação não encontrado.")));

        product.setPrice(productDTO.getPrice());

        product.setPicture(saveProductPicture(productDTO));

        saveProduct(product);

        if(!product.isActive()) {
            LocalDateTime now = LocalDateTime.now();

            InactiveProducts inactiveProduct = new InactiveProducts();
            inactiveProduct.setProduct(product);
            inactiveProduct.setDateTimeInactivation(now);
            inactiveProduct.setReasonForInactivation(productDTO.getReasonForInactivation());

            inactiveProductsRepository.save(inactiveProduct);
        }

    }

    public byte[] saveProductPicture(ProductDTO productDTO) {

        try {
            MultipartFile multipartFile = productDTO.getProductImage();
            if (!multipartFile.isEmpty()) {
                byte[] imageData = multipartFile.getBytes();
                return imageData;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Handle the error appropriately
        }

        return null;
    }

    public void populateProductDTO(ProductDTO productDTO, Product product) {

        productDTO.setActive(product.isActive());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setHeight(product.getHeight());
        productDTO.setWidth(product.getWidth());
        productDTO.setWeight(product.getWeight());
        productDTO.setLength(product.getLength());
        productDTO.setPurchaseAmount(product.getPurchaseAmount());

        List<Long> productCategoryIdList = product.getCategories().stream()
                .map(Category::getId)  // Map each Category to its id
                .collect(Collectors.toList());  // Collect the IDs into a list

        productDTO.setCategoriesIds(productCategoryIdList);
        productDTO.setPricingGroup(product.getPricingGroup().getId());
        productDTO.setPrice(product.getPrice());

        // find the Stock table by the product

        Stock stock = stockRepository.findByProductId(product.getId());

        productDTO.getStockDTO().setProductQuantityAvailable(stock.getProductQuantityAvailable());
        productDTO.setSupplier(stock.getSupplier().getId());

    }

    public void updateProduct(Product product, ProductDTO productDTO) {
        productDataSetter(product, productDTO);

        saveProduct(product);
    }

    public void inactivateProduct(Product product) {
        product.setActive(false);
        saveProduct(product);
    }


}
