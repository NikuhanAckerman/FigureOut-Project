package com.project.figureout.controller;

import com.project.figureout.model.Product;
import com.project.figureout.model.Stock;
import com.project.figureout.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public String showStockGet(Model model) {
        List<Stock> products =  stockService.getAllProductsInStock();
        model.addAttribute("products", products);

        return "stock";
    }

}
