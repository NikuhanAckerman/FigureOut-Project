package com.project.figureout.controller;

import com.project.figureout.dto.ClientDTO;
import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @GetMapping("/seeProducts")
    public String showProductsGet(Model model) {
        List<Product> products =  productService.getAllProducts();
        model.addAttribute("products", products);

        return "adminSeeProducts";
    }

    @GetMapping("/seeSales")
    public String showSalesGet(Model model) {
        List<Product> products =  productService.getAllProducts();
        model.addAttribute("products", products);

        return "adminSalesView";
    }

    @GetMapping("/createProduct")
    public String createProductGet(Model model) {
        List<Category> categoryList = productService.getAllCategories();
        List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();
        ProductDTO productDTO = new ProductDTO();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);
        model.addAttribute("productDTO", productDTO);

        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute ProductDTO productDTO, Model model) {
        Product product = new Product();

        productService.productDataSetter(product, productDTO);

        productService.saveProduct(product);

        return "redirect:/products/seeProducts";
    }

    @GetMapping("/productPicture/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        byte[] image = product.getPicture();
        System.out.println("Image byte length: " + image.length);
        if (image == null) {
            return ResponseEntity.notFound().build();  // Handle no image case
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or whatever image type you're using
                .body(image);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);

        return "redirect:/products/seeProducts";
    }

    @GetMapping("/shop")
    public String showShop(Model model) {
        List<Product> products =  productService.getAllProducts();
        model.addAttribute("products", products);

        return "shop";
    }

    @GetMapping("/specificProduct/{id}")
    public String showSpecificProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> productCategoryList = product.getCategories();
        //List<Category> categoryList = productService.getAllCategories();
        //List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();
        model.addAttribute("product", product);

        return "product";
    }

    @GetMapping("/updateProduct/{id}")
    public String updateProductGet(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        ProductDTO productDTO = new ProductDTO();

        productService.populateProductDTO(productDTO, product);

        List<Category> categoryList = productService.getAllCategories();
        List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productId", id);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);

        return "updateProduct";
    }

    @PutMapping("/updateProduct/{id}")
    public String updateProductPut(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, Model model) {
        Product product = productService.getProductById(id);

        productService.updateProduct(product, productDTO);

        return "redirect:/products/seeProducts";
    }

    @GetMapping("/getProductCategories/{id}")
    @ResponseBody
    public List<Category> getProductCategories(@PathVariable Long id) {
        return productService.getProductById(id).getCategories();
    }

/*
    @PostMapping("/products/addProductToCart/{productId}/{clientId}")
    public String addProductToCart(@PathVariable Long productId, @PathVariable Long clientId, Model model) {
        Product product = productService.getProductById(productId);
        Client client = clientService.getClientById(clientId);

        Cart cart = new Cart();


    }*/

}
