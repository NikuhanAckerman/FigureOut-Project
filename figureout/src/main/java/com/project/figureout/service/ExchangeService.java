package com.project.figureout.service;

import com.project.figureout.model.*;
import com.project.figureout.model.ExchangeProducts;
import com.project.figureout.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Getter @Setter
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ExchangeCouponRepository exchangeCouponRepository;

    private BigDecimal percentileToCreateNewExchangeCoupon = BigDecimal.valueOf(0.20); // if I use an exchange coupon and the
                                                                                      // leftover value is bigger than 100% + (percentile)%
                                                                                     // then I create a new exchange coupon with the value of
                                                                                    // the leftover.

    public void saveExchange(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

    public List<Exchange> getAllExchanges() {
        return exchangeRepository.findAll();
    }

    public Exchange getExchangeById(long id) {
        return exchangeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Troca não encontrada por ID."));
    }

    public void generateExchangeCoupon(Exchange exchange) {
        ExchangeCoupon newExchangeCoupon = new ExchangeCoupon();
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

        exchange.getClient().getExchangeCouponList().add(newExchangeCoupon);

//        clientService.saveClient(exchange.getClient());
    }

    public void generateExchangeCouponSurpass(Client client, BigDecimal amount) {
        ExchangeCoupon newExchangeCoupon = new ExchangeCoupon();
        newExchangeCoupon.setClient(client);

        char[][] allowedCharacterRanges = {{'a','z'},{'A','Z'},{'0','9'}};

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(allowedCharacterRanges)
                .build();

        String couponCode = "#" + generator.generate(10);

        /*RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z').build();
        String couponCode = generator.generate(15);*/

        newExchangeCoupon.setExchangeCouponCode(couponCode);

        newExchangeCoupon.setAmountWorth(amount);
        newExchangeCoupon.setUsed(false);

        exchangeCouponRepository.save(newExchangeCoupon);

        client.getExchangeCouponList().add(newExchangeCoupon);

//        clientService.saveClient(client);
    }


}
