package com.project.figureout.controller;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.CartRepository;
import com.project.figureout.repository.StockRepository;
import com.project.figureout.repository.SupplierRepository;
import com.project.figureout.service.CartService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.ProductService;
import com.project.figureout.service.StockService;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @Autowired
    CartService cartService;

    @Autowired
    private StockService stockService;

    @Autowired
    private SupplierRepository supplierRepository;

    //long clientId = 1;
    //Client client = clientService.getClientById(clientId);

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
        List<Supplier> supplierList = supplierRepository.findAll();
        ProductDTO productDTO = new ProductDTO();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("productDTO", productDTO);

        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute ProductDTO productDTO, Model model) {
        Product product = new Product();
        Stock stock = new Stock();

        productService.productDataSetter(product, productDTO);
        productService.saveProduct(product);

        stockService.productInStockDataSetter(stock, product, productDTO);
        stockService.saveProductInStock(stock);

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
        Client client = clientService.getClientById(1);
        model.addAttribute("products", products);
        model.addAttribute("cart", client.getCartList().getFirst());
        model.addAttribute("clientId", 1);
        return "shop";
    }

    @GetMapping("/specificProduct/{id}")
    public String showSpecificProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        Stock stock = stockService.getProductInStockByProductId(id);
        List<Category> productCategoryList = product.getCategories();
        Client client = clientService.getClientById(1);
        model.addAttribute("stock", stock);
        model.addAttribute("changeCartProductQuantityDTO", new ChangeCartProductQuantityDTO());
        model.addAttribute("product", product);
        model.addAttribute("cart", client.getCartList().getFirst());

        return "product";
    }

    @GetMapping("/updateProduct/{id}")
    public String updateProductGet(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Supplier> supplierList = supplierRepository.findAll();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setStockDTO(new StockDTO());

        productService.populateProductDTO(productDTO, product);

        List<Category> categoryList = productService.getAllCategories();
        List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productId", id);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);
        model.addAttribute("supplierList", supplierList);

        return "updateProduct";
    }

    @PutMapping("/updateProduct/{id}")
    public String updateProductPut(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, Model model) {
        Product product = productService.getProductById(id);
        Stock stock = stockService.getProductInStockByProductId(id);

        productService.updateProduct(product, productDTO);
        stockService.saveProductInStock(stock);

        return "redirect:/products/seeProducts";
    }

    @GetMapping("/getProductCategories/{id}")
    @ResponseBody
    public List<Category> getProductCategories(@PathVariable Long id) {
        return productService.getProductById(id).getCategories();
    }

}
