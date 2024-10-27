package com.project.figureout.service;

import com.project.figureout.model.*;
import com.project.figureout.model.ExchangeProducts;
import com.project.figureout.repository.*;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ExchangeCouponRepository exchangeCouponRepository;

    @Autowired
    private ClientService clientService;

    public void saveExchange(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

    public void generateExchangeCoupon(Exchange exchange) {
        ExchangeCoupon newExchangeCoupon = new ExchangeCoupon();
        newExchangeCoupon.setExchange(exchange);
        newExchangeCoupon.setClient(exchange.getClient());

        char[][] allowedCharacterRanges = {{'a','z'},{'A','Z'},{'0','9'}};

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(allowedCharacterRanges)
                .build();

        String couponCode = "#" + generator.generate(10);

        /*RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z').build();
        String couponCode = generator.generate(15);*/

        newExchangeCoupon.setExchangeCouponCode(couponCode);

        newExchangeCoupon.setAmountWorth(exchange.getFinalAmount());
        newExchangeCoupon.setUsed(false);

        exchangeCouponRepository.save(newExchangeCoupon);

        exchange.setCoupon(newExchangeCoupon);

        exchangeRepository.save(exchange);

    }



}
