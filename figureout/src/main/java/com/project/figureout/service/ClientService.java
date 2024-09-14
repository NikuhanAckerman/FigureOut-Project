package com.project.figureout.service;

import com.project.figureout.dto.AddressDTO;
import com.project.figureout.dto.ClientBasicDataDTO;
import com.project.figureout.dto.ClientDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    ClientRepository clientRepository;
    GenderRepository genderRepository;
    PhoneRepository phoneRepository;
    AddressRepository addressRepository;
    CreditCardRepository creditCardRepository;

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

    public void setGenderToClient(Gender gender, long id) {
        Client client = getClientById(id);

        client.setGender(gender);
    }

    public void setPhoneToClient(Phone phone, long id) {
        Client client = getClientById(id);

        client.setPhone(phone);
    }

    public void addAddressToClient(Address address, long id) {
        Client client = getClientById(id);


        client.getAddresses().add(address);
        address.setClient(client);

        addressRepository.save(address);
    }

    public void removeAddressFromClient(Address address, long id) {
        Client client = getClientById(id);

        client.getAddresses().remove(address);
    }

    public void addCreditCardToClient(CreditCard creditCard, long id) {
        Client client = getClientById(id);

        client.getCreditCards().add(creditCard);
        creditCard.setClient(client);

        creditCardRepository.save(creditCard);
    }

    public void removeCreditCardFromClient(CreditCard creditCard, long id) {
        Client client = getClientById(id);

        client.getCreditCards().remove(creditCard);
    }

    public void clientBasicDataSetter(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        client.setName(clientBasicDataDTO.getName());
        client.setEmail(clientBasicDataDTO.getEmail());
        client.setCpf(clientBasicDataDTO.getCpf());
        client.setPassword(clientBasicDataDTO.getPassword());
        client.setBirthday(clientBasicDataDTO.getBirthday());
        client.setEnabled(clientBasicDataDTO.isEnabled());
        client.setGender(clientBasicDataDTO.getGender());
        client.setPhone(clientBasicDataDTO.getPhone());
    }

    public void updateClientAdmin(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        clientBasicDataSetter(client, clientBasicDataDTO);

        clientRepository.save(client);
    }

    public void registerClient(Client client, ClientDTO clientDTO) {

        clientBasicDataSetter(client, clientDTO.getClientBasicDataDTO());

        AddressDTO clientDTODeliveryAddress = clientDTO.getDeliveryAddressDTO();
        AddressDTO clientDTOChargingAddress = clientDTO.getChargingAddressDTO();

        if(clientDTO.isSameAddress()) {
            Address address = new Address();

            address.setDeliveryAddress(true);
            address.setChargingAddress(true);
            address.setNickname(clientDTODeliveryAddress.getNickname());
            address.setTypeOfResidence(clientDTODeliveryAddress.getTypeOfResidence());
            address.setAddressing(clientDTODeliveryAddress.getAddressing());
            address.setHouseNumber(clientDTODeliveryAddress.getHouseNumber());
            address.setNeighbourhood(clientDTODeliveryAddress.getNeighbourhood());
            address.setAddressingType(clientDTODeliveryAddress.getAddressingType());
            address.setCep(clientDTODeliveryAddress.getCep());
            address.setCity(clientDTODeliveryAddress.getCity());
            address.setState(clientDTODeliveryAddress.getState());
            address.setCountry(clientDTODeliveryAddress.getCountry());
            address.setObservation(clientDTODeliveryAddress.getObservation());

            address.setClient(client);

            client.getAddresses().add(address);

            addressRepository.save(address);
        } else {
            Address deliveryAddress = new Address();
            Address chargingAddress = new Address();

            deliveryAddress.setDeliveryAddress(true);
            deliveryAddress.setNickname(clientDTODeliveryAddress.getNickname());
            deliveryAddress.setTypeOfResidence(clientDTODeliveryAddress.getTypeOfResidence());
            deliveryAddress.setAddressing(clientDTODeliveryAddress.getAddressing());
            deliveryAddress.setHouseNumber(clientDTODeliveryAddress.getHouseNumber());
            deliveryAddress.setNeighbourhood(clientDTODeliveryAddress.getNeighbourhood());
            deliveryAddress.setAddressingType(clientDTODeliveryAddress.getAddressingType());
            deliveryAddress.setCep(clientDTODeliveryAddress.getCep());
            deliveryAddress.setCity(clientDTODeliveryAddress.getCity());
            deliveryAddress.setState(clientDTODeliveryAddress.getState());
            deliveryAddress.setCountry(clientDTODeliveryAddress.getCountry());
            deliveryAddress.setObservation(clientDTODeliveryAddress.getObservation());
            deliveryAddress.setClient(client);

            chargingAddress.setChargingAddress(true);
            chargingAddress.setNickname(clientDTOChargingAddress.getNickname());
            chargingAddress.setTypeOfResidence(clientDTOChargingAddress.getTypeOfResidence());
            chargingAddress.setAddressing(clientDTOChargingAddress.getAddressing());
            chargingAddress.setHouseNumber(clientDTOChargingAddress.getHouseNumber());
            chargingAddress.setNeighbourhood(clientDTOChargingAddress.getNeighbourhood());
            chargingAddress.setAddressingType(clientDTOChargingAddress.getAddressingType());
            chargingAddress.setCep(clientDTOChargingAddress.getCep());
            chargingAddress.setCity(clientDTOChargingAddress.getCity());
            chargingAddress.setState(clientDTOChargingAddress.getState());
            chargingAddress.setCountry(clientDTOChargingAddress.getCountry());
            chargingAddress.setObservation(clientDTOChargingAddress.getObservation());
            chargingAddress.setClient(client);

            client.getAddresses().add(deliveryAddress);
            client.getAddresses().add(chargingAddress);

            addressRepository.save(deliveryAddress);
            addressRepository.save(chargingAddress);
        }

        clientRepository.save(client);

    }






}
