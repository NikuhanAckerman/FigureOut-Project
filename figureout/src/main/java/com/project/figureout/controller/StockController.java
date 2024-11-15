package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.SupplierRepository;
import com.project.figureout.service.*;
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

    private ClientNavigator clientNavigator;

    // Define o método para lidar com requisições GET para o caminho "/stock"
    @GetMapping("/stock")
    public String showStockGet(Model model) {
        // Chama o serviço para obter a lista de todos os produtos em estoque
        List<Stock> products = stockService.getAllProductsInStock();

        // Adiciona a lista de produtos ao modelo, com a chave "products"
        // Esse modelo será passado para a view que será renderizada
        model.addAttribute("products", products);

        // Retorna o nome da view que será renderizada. No caso, é "stock",
        // que o Spring vai associar ao arquivo "stock.html" ou "stock.jsp"
        return "stock";
    }

    // Define o método para lidar com requisições GET para o caminho "/createStock"
    @GetMapping("/createStock")
    public String createProductGet(Model model) {
        // Chama os serviços para obter a lista de todas os atributos necessários.
        List<Category> categoryList = productService.getAllCategories();
        List<PricingGroup> pricingGroupList = productService.getAllPricingGroups();
        List<Manufacturer> manufacturerList = manufacturerService.getAllManufacturers();
        List<Size> sizeList = sizeService.getAllSizes();

        // Chama o repositório para obter a lista de todos os fornecedores
        List<Supplier> supplierList = supplierRepository.findAll();

        // Cria um novo objeto ProductDTO que será o produto a ser gerenciado pelo estoque.
        ProductDTO productDTO = new ProductDTO();

        // Adiciona as listas e o objeto ProductDTO ao modelo, para que esses dados
        // fiquem disponíveis na view (a página HTML que será renderizada)
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pricingGroupList", pricingGroupList);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("productDTO", productDTO);  // Produto que será gerenciado pelo estoque.
        model.addAttribute("manufacturerList", manufacturerList);
        model.addAttribute("sizeList", sizeList);

        // Retorna o nome da view que será renderizada. Nesse caso, é "createStock",
        // que o Spring irá associar ao arquivo "createStock.html".
        return "createStock";
    }

}
