package com.project.figureout.service;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GenderRepository genderRepository;

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

    public void registerClient(Client client, ClientDTO clientDTO) {

        clientBasicDataSetter(client, clientDTO.getClientBasicDataDTO());

        Cart cart = new Cart();
        client.getCartList().add(cart);

        saveClient(client);
        cart.setClient(client);

        AddressDTO clientDTOAddress = clientDTO.getAddressDTO();
        addressService.registerAddress(client, clientDTOAddress);
    }

    public void updateClient(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        clientBasicDataSetter(client, clientBasicDataDTO);

        saveClient(client);
    }

    public List<Address> getClientAddresses(long id) {
        return getClientById(id).getAddresses();
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
        phone.setPhoneNumber(clientBasicDataDTO.getPhoneDTO().getPhoneNumber());

        client.setGender(gender);
        client.setPhone(phone);
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    public void populateClientBasicDataDTO(ClientBasicDataDTO clientBasicDataDTO, Client client) {

        clientBasicDataDTO.setName(client.getName());
        clientBasicDataDTO.setEmail(client.getEmail());
        clientBasicDataDTO.setCpf(addMaskToCpf(client.getCpf()));
        clientBasicDataDTO.setPassword(client.getPassword());
        clientBasicDataDTO.setBirthday(client.getBirthday());
        clientBasicDataDTO.setEnabled(client.isEnabled());

        clientBasicDataDTO.getGenderDTO().setId(client.getGender().getId());

        clientBasicDataDTO.getPhoneDTO().setCellphone(client.getPhone().isCellphone());
        clientBasicDataDTO.getPhoneDTO().setDdd(client.getPhone().getDdd());
        clientBasicDataDTO.getPhoneDTO().setPhoneNumber(client.getPhone().getPhoneNumber());
    }

    public String treatMaskedCpf(String cpf) {
        String treatedCpf;

        treatedCpf = cpf.replace(".", "");

        treatedCpf = treatedCpf.replace("-", "");

        return treatedCpf;

    }

    public String addMaskToCpf(String cpf) {
        // 123.456.789-00
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

}
