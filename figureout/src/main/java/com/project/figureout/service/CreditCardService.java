package com.project.figureout.service;

import com.project.figureout.dto.CreditCardDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.CreditCard;
import com.project.figureout.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CreditCardService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard getCreditCardById(long id) {
        return creditCardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cartão de crédito não encontrado com base no ID."));
    }

    public List<CreditCard> getClientCreditCards(long id) {
        return clientService.getClientById(id).getCreditCards();
    }

    public void addCreditCardToClient(Client client, CreditCard creditCard) {
        client.getCreditCards().add(creditCard);
        creditCard.setClient(client);

        creditCardRepository.save(creditCard);
    }

    public void deleteCreditCard(long id) {
        creditCardRepository.deleteById(id);
    }

    public void insertDataIntoCreditCard(CreditCard creditCard, CreditCardDTO creditCardDTO) {
        creditCard.setPreferido(creditCardDTO.isPreferido());
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setPrintedName(creditCardDTO.getPrintedName());
        creditCard.setBrand(creditCardDTO.getBrand());
        creditCard.setSecurityCode(creditCardDTO.getSecurityCode());
    }

    public void registerCreditCard(Client client, CreditCardDTO creditCardDTO) {
        CreditCard creditCard = new CreditCard();

        insertDataIntoCreditCard(creditCard, creditCardDTO);

        addCreditCardToClient(client, creditCard);
    }

    public void updateCreditCard(CreditCard creditCard, CreditCardDTO creditCardDTO) {
        insertDataIntoCreditCard(creditCard, creditCardDTO);

        creditCardRepository.save(creditCard);
    }

    // Population Methods

    // DTO Population Methods

    public void populateCreditCardDTO(CreditCardDTO creditCardDTO, CreditCard creditCard) {

        creditCardDTO.setPreferido(creditCard.isPreferido());
        creditCardDTO.setCardNumber(creditCard.getCardNumber());
        creditCardDTO.setPrintedName(creditCard.getPrintedName());
        creditCardDTO.setBrand(creditCard.getBrand());
        creditCardDTO.setSecurityCode(creditCard.getSecurityCode());

    }

}
