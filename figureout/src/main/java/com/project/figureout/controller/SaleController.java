package com.project.figureout.controller;

import com.project.figureout.ClientNavigator;
import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.PromotionalCouponRepository;
import com.project.figureout.repository.SaleRepository;
import com.project.figureout.repository.SalesCardsRepository;
import com.project.figureout.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("/sales")
@SessionAttributes({"salesCardsList", "deliveryAddress"})
public class SaleController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    ClientService clientService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private PromotionalCouponRepository promotionalCouponRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StockService stockService;

    private ClientNavigator clientNavigator;

    @GetMapping("")
    public String showSalesGet(Model model) {

        List<Sale> sales =  saleService.getAllSales();
        model.addAttribute("sales", sales);

        return "adminSalesView";
    }

    @GetMapping("/makeOrder/{cartId}")
    public String makeOrderGet(@PathVariable long cartId, Model model) {

        Cart cart = cartService.getCartById(cartId);
        Client client = cartService.getClientByCart(cart);
        List<Address> addressClientList = client.getAddresses();
        List<CreditCard> creditCardClientList = client.getCreditCards();
        ChangeCartProductQuantityDTO changeCartProductQuantityDTO = new ChangeCartProductQuantityDTO();
        PromotionalCouponDTO promotionalCouponDTO = new PromotionalCouponDTO();

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, BigDecimal> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getPriceToPay().multiply(BigDecimal.valueOf(cartsProducts.getProductQuantity())));
        }

        model.addAttribute("saleDTO", new SaleDTO());
        model.addAttribute("clientId", client.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartProductTotalPrices", cartProductTotalPrices);
        model.addAttribute("addressClientList", addressClientList);
        model.addAttribute("creditCardClientList", creditCardClientList);
        model.addAttribute("changeCartProductQuantityDTO", changeCartProductQuantityDTO);
        model.addAttribute("promotionalCouponDTO", promotionalCouponDTO);
        model.addAttribute("orderTotalPrice", cart.getTotalPrice());

        return "makeOrder";
    }

    @PostMapping("/makeOrder/{cartId}")
    public String makeOrderPost(@PathVariable long cartId, @ModelAttribute SaleDTO saleDTO, Model model, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCartById(cartId);
        System.out.println("address in saleDTO: " + saleDTO.getDeliveryAddressId());
        Address deliveryAddress = addressService.getAddressById(saleDTO.getDeliveryAddressId());

        List<SalesCards> salesCardsList = new ArrayList<>();

        for(long creditCardId: saleDTO.getSalesCardsIds()) {
            SalesCards currentSaleCard = new SalesCards();

            currentSaleCard.setCreditCard(creditCardService.getCreditCardById(creditCardId));

            salesCardsList.add(currentSaleCard);

        }

        if(salesCardsList.size() == 1) {
            salesCardsList.getFirst().setAmountPaid(cart.getTotalPrice());
        }

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, BigDecimal> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getPriceToPay().multiply(BigDecimal.valueOf(cartsProducts.getProductQuantity())));
        }

        model.addAttribute("salesCardsList", salesCardsList); // sessionattribute, to keep data for more than 1 request
        model.addAttribute("deliveryAddress", deliveryAddress);

        System.out.println("Lista de salescards: " + salesCardsList);


        redirectAttributes.addFlashAttribute("saleCart", cart);
        redirectAttributes.addFlashAttribute("cartProductTotalPrices", cartProductTotalPrices);
        redirectAttributes.addFlashAttribute("orderTotalPrice", cart.getTotalPrice());

        return "redirect:/sales/finishOrder/" + cartId;
    }

    @GetMapping("/finishOrder/{cartId}")
    public String finishOrderGet(@PathVariable long cartId, Model model) {

        Cart cart = cartService.getCartById(cartId);
        Client client = cartService.getClientByCart(cart);
        Address deliveryAddress = (Address) model.getAttribute("deliveryAddress");
        System.out.println(deliveryAddress.getNickname());
        List<SalesCards> listSalesCards = (List<SalesCards>) model.getAttribute("salesCardsList");

        SaleCardDTO saleCardDTO = new SaleCardDTO();
        model.addAttribute("saleCardDTO", saleCardDTO);

        BigDecimal freight = deliveryAddress.getState().getFreight();
        model.addAttribute("freight", freight);

        for(SalesCards saleCard: listSalesCards) {

            saleCardDTO.getAmountPaid().put(saleCard.getCreditCard().getId(), null);

        }

        System.out.println(saleCardDTO.getAmountPaid());

        BigDecimal amountFreight = cart.getTotalPrice().multiply(freight);

        BigDecimal saleFinalPrice = cart.getTotalPrice().add(amountFreight).setScale(2, RoundingMode.HALF_EVEN);

        System.out.println(saleFinalPrice);

        model.addAttribute("saleFinalPrice", saleFinalPrice);

        return "finishOrder";
    }

    @PostMapping("/finishOrder/{cartId}")
    public String createSale(@PathVariable long cartId, @ModelAttribute SaleCardDTO saleCardDTO, Model model,
                             @RequestParam("freight") BigDecimal freight,
                             @RequestParam("saleFinalPrice") BigDecimal saleFinalPrice) {
        System.out.println("O post do finish order rodou lol");
        Cart cart = cartService.getCartById(cartId);

        System.out.println("LOOOL" + saleCardDTO.getAmountPaid());

        Sale sale = new Sale();

        sale.setCart(cart);

        Address deliveryAddress = (Address) model.getAttribute("deliveryAddress");

        sale.setDeliveryAddress(deliveryAddress);

        List<SalesCards> listSalesCards = (List<SalesCards>) model.getAttribute("salesCardsList");
        System.out.println(listSalesCards);

        sale.setFreight(freight);
        System.out.println(sale.getFreight());

        saleService.saveSale(sale);

        for (Map.Entry<Long, BigDecimal> entry : saleCardDTO.getAmountPaid().entrySet()) {
            Long key = entry.getKey();
            BigDecimal value = entry.getValue();

            for(SalesCards saleCard: listSalesCards) {
                System.out.println(saleCard.getCreditCard().getId());

                if(saleCard.getCreditCard().getId() == key) {
                    System.out.println("it is equal lol");
                    SalesCardsKey salesCardsKey = new SalesCardsKey();
                    salesCardsKey.setCreditCardId(creditCardService.getCreditCardById(key).getId());
                    salesCardsKey.setSaleId(sale.getId());

                    saleCard.setId(salesCardsKey);

                    saleCard.setAmountPaid(value);
                    saleCard.setSale(sale);
                    sale.getCardsUsedInThisSale().add(saleCard);
                }

            }

        }

        saleService.saveSale(sale);

        sale.setStatus(SaleStatusEnum.EM_PROCESSAMENTO);

        sale.setPromotionalCouponApplied(sale.getCart().getPromotionalCoupon());

        Client client = clientService.getClientById(clientNavigator.getInstance().getClientId());

        sale.setFinalPrice(saleFinalPrice);
        System.out.println(sale.getFinalPrice());

        saleService.saveSale(sale);

        cartService.changeClientCart(client);

        return "redirect:/products/shop";
    }

    @GetMapping("/seeSales")
    public String seeSales(Model model) {

        model.addAttribute("changeSaleStatusDTO", new ChangeSaleStatusDTO());
        model.addAttribute("sales", saleService.getAllSales());

        return "adminSalesView";
    }

    @PutMapping("/seeSales/changeSaleStatus/{saleId}")
    public String changeSaleStatus(@PathVariable long saleId, @ModelAttribute ChangeSaleStatusDTO changeSaleStatusDTO) {
        Sale sale = saleService.getSaleById(saleId);

        if(sale.getStatus().equals(SaleStatusEnum.EM_PROCESSAMENTO)) {

            if(changeSaleStatusDTO.getStatus().equals(SaleStatusEnum.PAGAMENTO_REALIZADO)) {
                HashMap<Stock, Integer> cartProductQuantityToDrop = new HashMap<>();

                for(CartsProducts cartProduct : sale.getCart().getCartProducts()) {
                    Stock stock = stockService.getProductInStockByProductId(cartProduct.getProduct().getId());

                    cartProductQuantityToDrop.put(stock, cartProduct.getProductQuantity());

                    if(cartProduct.getProductQuantity() >= stock.getProductQuantityAvailable()) {
                        productService.inactivateProduct(stock.getProduct());
                    }
                }

                stockService.dropInStockList(cartProductQuantityToDrop);

            }

        }

        sale.setStatus(changeSaleStatusDTO.getStatus());

        saleService.saveSale(sale);

        return "redirect:/sales/seeSales";
    }

    @GetMapping("/getSaleCartProducts/{saleId}")
    @ResponseBody
    public List<CartsProducts> getSaleCartProducts(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getCart().getCartProducts();
    }

    @GetMapping("/getSaleClientId/{saleId}")
    @ResponseBody
    public long getSaleClientId(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getCart().getClient().getId();
    }

    @GetMapping("/getSaleClientName/{saleId}")
    @ResponseBody
    public String getSaleClientName(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getCart().getClient().getName();
    }

    @GetMapping("/getSaleTotal/{saleId}")
    @ResponseBody
    public BigDecimal getSaleTotal(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        System.out.println(sale.getFinalPrice());

        return sale.getFinalPrice();
    }

    @GetMapping("/getFreight/{saleId}")
    @ResponseBody
    public BigDecimal getFreight(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getFreight();
    }

    @GetMapping("/getPromotionalCoupon/{saleId}")
    @ResponseBody
    public ResponseEntity<?> getPromotionalCoupon(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);
        PromotionalCoupon coupon = sale.getCart().getPromotionalCoupon();

        if (coupon == null) {
            return ResponseEntity.ok().body(Collections.emptyMap()); // Return an empty map if the coupon is null
        }
        return ResponseEntity.ok(coupon); // Otherwise, return the coupon
    }


}
