package com.project.figureout.service;

import com.project.figureout.model.Client;
import com.project.figureout.model.CreditCard;
import com.project.figureout.model.Exchange;
import com.project.figureout.repository.CartsProductsRepository;
import com.project.figureout.repository.CreditCardBrandRepository;
import com.project.figureout.repository.CreditCardRepository;
import com.project.figureout.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ClientService clientService;

    public void saveExchange(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

    public Exchange getExchangeById(long id) {
        return exchangeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Troca não encontrada com base no ID."));
    }

    public List<Exchange> getClientExchanges(long id) {
        return clientService.getClientById(id).getExchangeList();
    }

}
