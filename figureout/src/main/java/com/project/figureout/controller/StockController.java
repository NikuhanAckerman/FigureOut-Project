package com.project.figureout.controller;

import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.SupplierRepository;
import com.project.figureout.service.ManufacturerService;
import com.project.figureout.service.ProductService;
import com.project.figureout.service.SizeService;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/stock")
    public String showStockGet(Model model) {
        List<Stock> products =  stockService.getAllProductsInStock();
        model.addAttribute("products", products);

        return "stock";
    }

    @GetMapping("/createStock")
    public String createProductGet(Model model) {
        List<Category> categoryList = productService.getAllCategories();
        List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();
        List<Supplier> supplierList = supplierRepository.findAll();
        List<Manufacturer> manufacturerList = manufacturerService.getAllManufacturers();
        List<Size> sizeList = sizeService.getAllSizes();
        ProductDTO productDTO = new ProductDTO();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("manufacturerList", manufacturerList);
        model.addAttribute("sizeList", sizeList);

        return "createStock";
    }

}
