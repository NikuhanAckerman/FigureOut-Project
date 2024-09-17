package com.project.figureout.service;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    // Client Methods

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com base no ID."));
    }

    public void deleteClientById(long id) {
        clientRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void saveClientList(List<Client> clientList) {
        clientRepository.saveAll(clientList);
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    public void updateClientAdmin(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        clientBasicDataSetter(client, clientBasicDataDTO);

        saveClient(client);
    }

    public void registerClient(Client client, ClientDTO clientDTO) {

        clientBasicDataSetter(client, clientDTO.getClientBasicDataDTO());

        saveClient(client);

        AddressDTO clientDTOAddress = clientDTO.getAddressDTO();

        registerAddress(client, clientDTOAddress);

    }

    public void updateClient(Client client, ClientBasicDataDTO clientBasicDataDTO) {

        if(clientBasicDataDTO.getConfirmPassword().equals(clientBasicDataDTO.getPassword())) {
            clientBasicDataSetter(client, clientBasicDataDTO);

            saveClient(client);
        } else {
            throw new IllegalArgumentException("Senhas não batem.");
        }

    }

    public void clientBasicDataSetter(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        System.out.println("calling basic data setter");
        client.setName(clientBasicDataDTO.getName());
        client.setEmail(clientBasicDataDTO.getEmail());
        client.setCpf(treatMaskedCpf(clientBasicDataDTO.getCpf()));
        client.setPassword(clientBasicDataDTO.getPassword());
        client.setBirthday(clientBasicDataDTO.getBirthday());
        client.setEnabled(clientBasicDataDTO.isEnabled());

        Gender gender = new Gender();
        Phone phone = new Phone();

        gender.setId(clientBasicDataDTO.getGenderDTO().getId());

        phone.setCellphone(clientBasicDataDTO.getPhoneDTO().isCellphone());
        phone.setDdd(clientBasicDataDTO.getPhoneDTO().getDdd());
        phone.setPhoneNumber(treatMaskedPhoneNumber(clientBasicDataDTO.getPhoneDTO().getPhoneNumber()));

        client.setGender(gender);
        client.setPhone(phone);
    }

    // Gender Methods

    public void setGenderToClient(Gender gender, long id) {
        Client client = getClientById(id);

        client.setGender(gender);
    }

    // Phone Methods

    public void setPhoneToClient(Phone phone, long id) {
        Client client = getClientById(id);

        client.setPhone(phone);
    }

    // Address Methods

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public Address getAddressById(long id) {
        return addressRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com base no ID."));
    }

    public List<Address> getClientAddresses(long id) {
        return getClientById(id).getAddresses();
    }

    public void addAddressToClient(Client client, Address address) {
        client.getAddresses().add(address);
        address.setClient(client);

        saveAddress(address);
    }

    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    public void registerAddress(Client client, AddressDTO addressDTO) {
        Address address = new Address();

        address.setDeliveryAddress(addressDTO.isDeliveryAddress());
        address.setChargingAddress(addressDTO.isChargingAddress());
        address.setNickname(addressDTO.getNickname());
        address.setTypeOfResidence(addressDTO.getTypeOfResidence());
        address.setAddressing(addressDTO.getAddressing());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setNeighbourhood(addressDTO.getNeighbourhood());
        address.setAddressingType(addressDTO.getAddressingType());
        address.setCep(treatMaskedCep(addressDTO.getCep()));
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setObservation(addressDTO.getObservation());

        addAddressToClient(client, address);
    }

    public void updateAddress(long id, AddressDTO addressDTO) {
        Address address = getAddressById(id);

        address.setDeliveryAddress(addressDTO.isDeliveryAddress());
        address.setChargingAddress(addressDTO.isChargingAddress());
        address.setNickname(addressDTO.getNickname());
        address.setTypeOfResidence(addressDTO.getTypeOfResidence());
        address.setAddressing(addressDTO.getAddressing());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setNeighbourhood(addressDTO.getNeighbourhood());
        address.setAddressingType(addressDTO.getAddressingType());
        address.setCep(addressDTO.getCep());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setObservation(addressDTO.getObservation());

        saveAddress(address);
    }

    // Credit Card Methods

    public CreditCard getCreditCardById(long id) {
        return creditCardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cartão de crédito não encontrado com base no ID."));
    }

    public List<CreditCard> getClientCreditCards(long id) {
        return getClientById(id).getCreditCards();
    }

    public void addCreditCardToClient(Client client, CreditCard creditCard) {
        client.getCreditCards().add(creditCard);
        creditCard.setClient(client);

        creditCardRepository.save(creditCard);
    }

    public void deleteCreditCard(long id) {
        creditCardRepository.deleteById(id);
    }

    public void registerCreditCard(Client client, CreditCardDTO creditCardDTO) {
        CreditCard creditCard = new CreditCard();

        creditCard.setPreferido(creditCardDTO.isPreferido());
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setPrintedName(creditCardDTO.getPrintedName());
        creditCard.setBrand(creditCardDTO.getBrand());
        creditCard.setSecurityCode(creditCardDTO.getSecurityCode());

        addCreditCardToClient(client, creditCard);
    }

    public void updateCreditCard(CreditCard creditCard, CreditCardDTO creditCardDTO) {
        creditCard.setPreferido(creditCardDTO.isPreferido());
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setPrintedName(creditCardDTO.getPrintedName());
        creditCard.setBrand(creditCardDTO.getBrand());
        creditCard.setSecurityCode(creditCardDTO.getSecurityCode());

        creditCardRepository.save(creditCard);
    }

    // Population Methods

    // DTO Population Methods

    public void populateAddressDTO(AddressDTO addressDTO, Address address) {
        addressDTO.setDeliveryAddress(address.isDeliveryAddress());
        addressDTO.setChargingAddress(address.isChargingAddress());
        addressDTO.setNickname(address.getNickname());
        addressDTO.setTypeOfResidence(address.getTypeOfResidence());
        addressDTO.setAddressing(address.getAddressing());
        addressDTO.setHouseNumber(address.getHouseNumber());
        addressDTO.setNeighbourhood(address.getNeighbourhood());
        addressDTO.setAddressingType(address.getAddressingType());
        addressDTO.setCep(address.getCep());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setObservation(address.getObservation());
    }

    public void populateCreditCardDTO(CreditCardDTO creditCardDTO, CreditCard creditCard) {

        creditCardDTO.setPreferido(creditCard.isPreferido());
        creditCardDTO.setCardNumber(creditCard.getCardNumber());
        creditCardDTO.setPrintedName(creditCard.getPrintedName());
        creditCardDTO.setBrand(creditCard.getBrand());
        creditCardDTO.setSecurityCode(creditCard.getSecurityCode());

    }

    public void populateClientBasicDataDTO(ClientBasicDataDTO clientBasicDataDTO, Client client) {

        clientBasicDataDTO.setName(client.getName());
        clientBasicDataDTO.setEmail(client.getEmail());
        clientBasicDataDTO.setCpf(client.getCpf());
        clientBasicDataDTO.setPassword(client.getPassword());
        clientBasicDataDTO.setBirthday(client.getBirthday());
        clientBasicDataDTO.setEnabled(client.isEnabled());

        GenderDTO genderDTO = new GenderDTO();
        PhoneDTO phoneDTO = new PhoneDTO();

        genderDTO.setId(client.getGender().getId());
        clientBasicDataDTO.setGenderDTO(genderDTO);

        phoneDTO.setCellphone(client.getPhone().isCellphone());
        phoneDTO.setDdd(client.getPhone().getDdd());
        phoneDTO.setPhoneNumber(client.getPhone().getPhoneNumber());
        clientBasicDataDTO.setPhoneDTO(phoneDTO);

    }

    // Validation Methods

    // Data treatment methods

    public String treatMaskedCpf(String cpf) {
        String treatedCpf;

        treatedCpf = cpf.replace(".", "");

        treatedCpf = treatedCpf.replace("-", "");

        return treatedCpf;

    }

    public String treatMaskedCep(String cep) {
        String treatedCep;

        treatedCep = cep.replace("-", "");

        return treatedCep;
    }

    public String treatMaskedPhoneNumber(String phoneNumber) {
        String treatedPhoneNumber;

        treatedPhoneNumber = phoneNumber.replace("-", "");

        return treatedPhoneNumber;
    }


}
