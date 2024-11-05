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
import org.apache.commons.text.RandomStringGenerator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/sales")
@SessionAttributes({"salesCardsList", "deliveryAddress", "cartProductTotalPrices"})
public class SaleController {

    @Autowired
    CartService cartService;

    @Autowired
    private LogService logService;

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

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    NotificationService notificationService;

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
        ExchangeCouponIndividualDTO exchangeCouponIndividualDTO = new ExchangeCouponIndividualDTO();

        List<CartsProducts> cartsProductsList = cart.getCartProducts();

        HashMap<Long, BigDecimal> cartProductTotalPrices = new HashMap<>();

        for(CartsProducts cartsProducts : cartsProductsList) {
            // multiply product price by product quantity
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getFinalPrice());
        }

        model.addAttribute("saleDTO", new SaleDTO());
        model.addAttribute("clientId", client.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartProductTotalPrices", cartProductTotalPrices);
        model.addAttribute("addressClientList", addressClientList);
        model.addAttribute("creditCardClientList", creditCardClientList);
        model.addAttribute("changeCartProductQuantityDTO", changeCartProductQuantityDTO);
        model.addAttribute("promotionalCouponDTO", promotionalCouponDTO);
        model.addAttribute("exchangeCouponIndividualDTO", exchangeCouponIndividualDTO);
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
            cartProductTotalPrices.put(cartsProducts.getProduct().getId(), cartsProducts.getFinalPrice());
        }

        model.addAttribute("salesCardsList", salesCardsList); // sessionattribute, to keep data for more than 1 request
        model.addAttribute("deliveryAddress", deliveryAddress);
        model.addAttribute("cartProductTotalPrices", cartProductTotalPrices);

        System.out.println("Lista de salescards: " + salesCardsList);

        redirectAttributes.addFlashAttribute("saleCart", cart);

        redirectAttributes.addFlashAttribute("orderTotalPrice", cart.getTotalPrice());

        // log de transação do método.
        //Client navigator = clientService.getClientById(clientNavigator.getInstance().getClientId());
        //logService.logTransaction(String.valueOf(navigator), "insert", cart.toString());

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
                             @RequestParam("saleFinalPrice") BigDecimal saleFinalPrice,
                             HttpServletRequest request) {

        Cart cart = cartService.getCartById(cartId);

        Sale sale = new Sale();

        char[][] allowedCharacterRanges = {{'a','z'},{'A','Z'},{'0','9'}};

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(allowedCharacterRanges)
                .build();

        String saleCode = "#" + generator.generate(6);

        sale.setSaleCode(saleCode);

        sale.setCart(cart);

        Address deliveryAddress = (Address) model.getAttribute("deliveryAddress");
        sale.setDeliveryAddress(deliveryAddress);

        List<SalesCards> listSalesCards = (List<SalesCards>) model.getAttribute("salesCardsList");

        sale.setFreight(freight);
        sale.setPromotionalCouponApplied(sale.getCart().getPromotionalCoupon());
        sale.setFinalPrice(saleFinalPrice);

        // declaring the total amount paid by the credit cards and
        BigDecimal totalPaidByCards = BigDecimal.valueOf(0);
        // errors list
        List<String> errors = new ArrayList<>();

        for (Map.Entry<Long, BigDecimal> entry : saleCardDTO.getAmountPaid().entrySet()) {
            Long key = entry.getKey();
            BigDecimal value = entry.getValue();

            for (SalesCards saleCard : listSalesCards) {
                if (saleCard.getCreditCard().getId() == key) {

                    saleCard.setAmountPaid(value);

                    // incrementing the variable according to each amount paid by the cards
                    totalPaidByCards = totalPaidByCards.add(saleCard.getAmountPaid());

                }
            }
        }


        if (sale.getPromotionalCouponApplied() == null && sale.getExchangeCouponsApplied().isEmpty()) {

            for (SalesCards saleCard : listSalesCards) {

                // checking if the value paid is bigger than R$10 if, and only if, there is no promotional coupon or exchangeCoupon
                if (saleCard.getAmountPaid().compareTo(BigDecimal.valueOf(10.00)) < 0) {
                    errors.add("O valor pago pelo cartão " + saleCard.getCreditCard().getCardNumber() + " não pode ser inferior a R$10,00.");
                }

            }

        }

        System.out.println("Preço final da venda: " + saleFinalPrice);
        System.out.println("Total pago pelos cartões: " + totalPaidByCards);

        // seeing if the value paid by cards is equal the total sale price

        if (totalPaidByCards.compareTo(saleFinalPrice) != 0) { // 0 = (values are equal)
            errors.add("O total pago pelos cartões é excedente ou insuficiente para pagar pela compra.");
        }

        HashMap<Long, BigDecimal> cartProductTotalPrice = (HashMap<Long, BigDecimal>) model.getAttribute("cartProductTotalPrice");

        if (!errors.isEmpty()) {

            model.addAttribute("errors", errors);
            model.addAttribute("cartProductTotalPrice", cartProductTotalPrice);
            model.addAttribute("saleCart", cart);
            model.addAttribute("saleFinalPrice", saleFinalPrice);
            model.addAttribute("freight", freight);
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("salesCardsList", listSalesCards);
            model.addAttribute("saleCardDTO", saleCardDTO);

            return "finishOrder";
        }

        saleService.saveSale(sale);

        for (Map.Entry<Long, BigDecimal> entry : saleCardDTO.getAmountPaid().entrySet()) {
            Long key = entry.getKey();

            for (SalesCards saleCard : listSalesCards) {
                System.out.println(saleCard.getCreditCard().getId());

                if (saleCard.getCreditCard().getId() == key) {
                    SalesCardsKey salesCardsKey = new SalesCardsKey();
                    salesCardsKey.setCreditCardId(creditCardService.getCreditCardById(key).getId());
                    salesCardsKey.setSaleId(sale.getId());

                    saleCard.setId(salesCardsKey);
                    saleCard.setSale(sale);

                    sale.getCardsUsedInThisSale().add(saleCard);

                }

            }

        }

        for(ExchangeCoupon exchangeCoupon: sale.getExchangeCouponsApplied()) {
            if(cartService.isExchangeCouponSurpassingCartTotalTooMuch(cart, exchangeCoupon).compareTo(BigDecimal.valueOf(0)) > 0) {
                exchangeService.generateExchangeCouponSurpass(cart.getClient(), cartService.isExchangeCouponSurpassingCartTotalTooMuch(cart, exchangeCoupon));
            }
        }

        sale.setStatus(SaleStatusEnum.EM_PROCESSAMENTO);

        LocalDateTime now = LocalDateTime.now();

        sale.setDateTimeSale(now);

        Client client = clientService.getClientById(clientNavigator.getInstance().getClientId());

        saleService.saveSale(sale);

        cartService.changeClientCart(client);

        // creating notification
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setCategory(NotificationCategoryEnum.VENDA);
        notificationDTO.setTitle("Compra realizada!");
        notificationDTO.setDescription("Sua compra de R$" + sale.getFinalPrice() + "foi realizada com sucesso.");
        notificationService.createNotification(client, notificationDTO);

        clientService.recalculateClientRanking();

        // log de transação do método.
        //Client navigator = clientService.getClientById(clientNavigator.getInstance().getClientId());
        //logService.logTransaction(String.valueOf(navigator), "insert", sale.toString());

        return "redirect:/products/shop";
    }

    @GetMapping("/seeSales")
    public String seeSales(Model model) {

        model.addAttribute("changeSaleStatusDTO", new ChangeSaleStatusDTO());
        model.addAttribute("changeSaleDateTimeDTO", new ChangeSaleDateTimeDTO());
        model.addAttribute("status", SaleStatusEnum.values());
        model.addAttribute("sales", saleService.getAllSales());
        model.addAttribute("exchanges", exchangeService.getAllExchanges());

        return "adminSalesView";
    }

    @PutMapping("/seeSales/changeSaleStatus/{saleId}")
    public String changeSaleStatus(@PathVariable long saleId, @ModelAttribute ChangeSaleStatusDTO changeSaleStatusDTO) {
        saleService.changeSaleStatus(saleService.getSaleById(saleId), changeSaleStatusDTO);

        return "redirect:/sales/seeSales";
    }

    @GetMapping("/getSaleExchangeList/{saleId}")
    @ResponseBody
    public List<Exchange> getSaleExchangeList(@PathVariable long saleId) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getExchangeList();
    }

    @GetMapping("/getSpecificExchange/{exchangeId}")
    @ResponseBody
    public Exchange getSpecificExchange(@PathVariable long exchangeId) {
        Exchange exchange = exchangeService.getExchangeById(exchangeId);

        return exchange;
    }

    @GetMapping("/getSaleCartId/{saleId}")
    @ResponseBody
    public long getSaleCartId(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getCart().getId();
    }

    @GetMapping("/getSaleDate/{saleId}")
    @ResponseBody
    public LocalDateTime getSaleDate(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getDateTimeSale();
    }

    @GetMapping("/getSaleCode/{saleId}")
    @ResponseBody
    public String getSaleCode(@PathVariable long saleId, Model model) {
        Sale sale = saleService.getSaleById(saleId);

        return sale.getSaleCode();
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

    @PutMapping("/seeSales/changeSaleDateTime/{saleId}")
    public String changeSaleDateTime(@PathVariable long saleId, @ModelAttribute ChangeSaleDateTimeDTO changeSaleDateTimeDTO) {
        Sale sale = saleService.getSaleById(saleId);

        sale.setDateTimeSale(changeSaleDateTimeDTO.getNewDateTime());
        
        saleService.saveSale(sale);

        return "redirect:/sales/seeSales";
    }



}
