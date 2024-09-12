package com.project.figureout.controller;

import com.project.figureout.dto.*;
import com.project.figureout.model.Address;
import com.project.figureout.model.CreditCard;
import com.project.figureout.model.Gender;
import com.project.figureout.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.project.figureout.model.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    @GetMapping({"", "/", "/index"})
    public String showClientsGet(Model model) {

        List<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);

        return "index";
    }

    @GetMapping("index/{id}/addresses") // exemplo de paranaue com javascript (o codigo js ta na pagina index)
    @ResponseBody
    public List<Address> getClientAddresses(@PathVariable long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        return client.getAddresses();
    }

    @GetMapping("/createAddress/{id}")
    public String addClientGet(Model model, @PathVariable long id) {
        AddressDTO addressDTO = new AddressDTO();

        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("clientId", id);

        return "createAddress";
    }

    @PostMapping("createAddress/{id}")
    public String createClientAddressPost(@PathVariable long id, @ModelAttribute("addressDTO") AddressDTO addressDTO) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        Address address = new Address();

        address.setAddressType(addressDTO.isAddressType());
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

        client.addAddress(address);

        clientRepository.save(client);

        return "redirect:/index";
    }

    @DeleteMapping("index/{id}/addresses/delete")
    public String deleteClientAddress(@PathVariable long id) {
        addressRepository.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("updateAddress/{addressId}")
    public String updateClientAddressGet(@PathVariable long addressId, Model model) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressType(address.isAddressType());
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

        model.addAttribute("addressDTO", addressDTO);

        return "updateAddress";
    }

    @PutMapping("updateAddress/{addressId}")
    public String updateClientAddressPost(@PathVariable long addressId, @ModelAttribute("addressDTO") AddressDTO addressDTO) {
        Address addressToUpdate = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));

        addressToUpdate.setAddressType(addressDTO.isAddressType());
        addressToUpdate.setNickname(addressDTO.getNickname());
        addressToUpdate.setTypeOfResidence(addressDTO.getTypeOfResidence());
        addressToUpdate.setAddressing(addressDTO.getAddressing());
        addressToUpdate.setHouseNumber(addressDTO.getHouseNumber());
        addressToUpdate.setNeighbourhood(addressDTO.getNeighbourhood());
        addressToUpdate.setAddressingType(addressDTO.getAddressingType());
        addressToUpdate.setCep(addressDTO.getCep());
        addressToUpdate.setCity(addressDTO.getCity());
        addressToUpdate.setState(addressDTO.getState());
        addressToUpdate.setCountry(addressDTO.getCountry());
        addressToUpdate.setObservation(addressDTO.getObservation());

        addressRepository.save(addressToUpdate);

        return "redirect:/index";
    }

    @GetMapping("index/{id}/creditCards")
    @ResponseBody
    public List<CreditCard> getClientCreditCards(@PathVariable long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        return client.getCreditCards();
    }

    @GetMapping("createCreditCard/{id}")
    public String createClientCreditCardGet(@PathVariable long id, Model model) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        CreditCardDTO creditCardDTO = new CreditCardDTO();

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("clientId", id);

        return "createCreditCard";
    }

    @PostMapping("createCreditCard/{id}")
    public String createClientCreditCardPost(@PathVariable long id, @ModelAttribute CreditCardDTO creditCardDTO) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));

        CreditCard creditCard = new CreditCard();

        creditCard.setPreferido(creditCardDTO.isPreferido());
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setPrintedName(creditCardDTO.getPrintedName());
        creditCard.setBrand(creditCardDTO.getBrand());
        creditCard.setSecurityCode(creditCardDTO.getSecurityCode());

        client.addCreditCard(creditCard);

        clientRepository.save(client);

        return "redirect:/index";

    }

    @DeleteMapping("index/{id}/creditCards/delete")
    public String deleteClientCreditCard(@PathVariable long id) {
        creditCardRepository.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("updateCreditCard/{id}")
    public String updateClientCreditCardGet(@PathVariable long id, Model model) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));
        CreditCardDTO creditCardDTO = new CreditCardDTO();

        creditCardDTO.setPreferido(creditCard.isPreferido());
        creditCardDTO.setCardNumber(creditCard.getCardNumber());
        creditCardDTO.setPrintedName(creditCard.getPrintedName());
        creditCardDTO.setBrand(creditCard.getBrand());
        creditCardDTO.setSecurityCode(creditCard.getSecurityCode());

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("creditCardId", id);



        return "updateCreditCard";

    }

    @PutMapping("updateCreditCard/{id}")
    public String updateClientCreditCardPut(@PathVariable long id, @ModelAttribute CreditCardDTO creditCardDTO) {
        CreditCard creditCardToUpdate = creditCardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inexistente."));

        creditCardToUpdate.setPreferido(creditCardDTO.isPreferido());
        creditCardToUpdate.setCardNumber(creditCardDTO.getCardNumber());
        creditCardToUpdate.setPrintedName(creditCardDTO.getPrintedName());
        creditCardToUpdate.setBrand(creditCardDTO.getBrand());
        creditCardToUpdate.setSecurityCode(creditCardDTO.getSecurityCode());

        creditCardRepository.save(creditCardToUpdate);

        return "redirect:/index";
    }

    @GetMapping("/createClient")
    public String addClientGet(Model model) {
        ClientDTO clientDTO = new ClientDTO();

        List<Gender> genderList = genderRepository.findAll();

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("genderList", genderList);

        return "createClient";
    }

    @PostMapping("/createClient")
    public String addClientPost(@ModelAttribute("clientDTO") ClientDTO clientDTO) {

        Gender gender = genderRepository.findById(clientDTO.getGender().getId()).orElse(null);

        phoneRepository.save(clientDTO.getPhone());

        Client client = new Client();
        client.setPhone(clientDTO.getPhone());
        client.setGender(gender);
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setCpf(clientDTO.getCpf());
        client.setBirthday(clientDTO.getBirthday());
        client.setEnabled(clientDTO.isEnabled());

        String password = clientDTO.getPassword();
        String confirmedPassword = clientDTO.getConfirmPassword();

        if(password.equals(confirmedPassword)) {
            client.setPassword(password);
        } else {
            throw new IllegalArgumentException("As senhas digitadas não batem.");
        }

        clientDTO.getDeliveryAddress().setAddressType(false);
        clientDTO.getChargingAddress().setAddressType(true);

        addressRepository.save(clientDTO.getDeliveryAddress());
        addressRepository.save(clientDTO.getChargingAddress());

        client.addAddress(clientDTO.getDeliveryAddress());
        client.addAddress(clientDTO.getChargingAddress());

        clientRepository.save(client);

        // Validação da senha
//        if (!client.isValidPassword(clientDTO.getPassword())) {
//            return "Senha inválida. A senha deve ter pelo menos 8 caracteres, incluir letras maiúsculas, minúsculas e caracteres especiais.";
//        }

        return "redirect:/index";
    }

    @GetMapping("/updateClient/{id}")
    public String updateClientGet(@PathVariable long id, Model model) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido."));

        ClientAdminUpdateDTO ClientAdminUpdateDTO = new ClientAdminUpdateDTO();
        ClientAdminUpdateDTO.setName(client.getName());
        ClientAdminUpdateDTO.setEmail(client.getEmail());
        ClientAdminUpdateDTO.setPassword(client.getPassword());
        ClientAdminUpdateDTO.setCpf(client.getCpf());
        ClientAdminUpdateDTO.setBirthday(client.getBirthday());
        ClientAdminUpdateDTO.setEnabled(client.isEnabled());
        ClientAdminUpdateDTO.setGender(client.getGender());
        ClientAdminUpdateDTO.setPhone(client.getPhone());

        List<Gender> genderList = genderRepository.findAll();
        model.addAttribute("clientAdminUpdateDTO", ClientAdminUpdateDTO);
        model.addAttribute("genderList", genderList);
        model.addAttribute("clientId", id);

        return "updateClient";

    }

    @PutMapping("/updateClient/{id}")
    public String updateClient(@PathVariable long id, @ModelAttribute ClientAdminUpdateDTO clientAdminUpdateDTO) {

        Client clientToUpdate = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido."));

        clientToUpdate.setName(clientAdminUpdateDTO.getName());
        clientToUpdate.setEmail(clientAdminUpdateDTO.getEmail());
        clientToUpdate.setPassword(clientAdminUpdateDTO.getPassword());
        clientToUpdate.setCpf(clientAdminUpdateDTO.getCpf());
        clientToUpdate.setBirthday(clientAdminUpdateDTO.getBirthday());
        clientToUpdate.setEnabled(clientAdminUpdateDTO.isEnabled());
        clientToUpdate.setGender(clientAdminUpdateDTO.getGender());
        clientToUpdate.setPhone(clientAdminUpdateDTO.getPhone());

        clientRepository.save(clientToUpdate);

        return "redirect:/index";

    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {
        clientRepository.deleteById(id);

        return "redirect:/index";
    }



}
