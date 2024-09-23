package com.project.figureout.controller;

import com.project.figureout.dto.ClientDTO;
import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

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

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute ProductDTO productDTO, Model model) {
        Product product = new Product();




    }




}
