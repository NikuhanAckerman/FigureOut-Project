package com.project.figureout.controller;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import com.project.figureout.service.AddressService;
import com.project.figureout.service.ClientService;
import com.project.figureout.service.CreditCardService;
import com.project.figureout.service.StateAndCountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StateAndCountryService stateAndCountryService;

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping({"", "/", "/index"})
    public String showClientsGet(Model model) {

        List<Client> clients =  clientService.getAllClients();
        model.addAttribute("clients", clients);

        return "index";
    }

    @GetMapping("index/{id}/addresses") // exemplo de paranaue com javascript (o codigo js ta na pagina index)
    @ResponseBody
    public List<Address> getClientAddresses(@PathVariable long id) {
        return clientService.getClientAddresses(id);
    }

    @GetMapping("/createAddress/{id}")
    public String addClientGet(Model model, @PathVariable long id) {
        AddressDTO addressDTO = new AddressDTO();

        List<State> stateList = stateAndCountryService.getAllStates();
        List<Country> countryList = stateAndCountryService.getAllCountries();

        model.addAttribute("stateList", stateList);
        model.addAttribute("countryList", countryList);
        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("clientId", id);

        return "createAddress";
    }

    @PostMapping("createAddress/{id}")
    public String createClientAddressPost(@PathVariable long id, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult result, Model model) {
        Client client = clientService.getClientById(id);

        if(result.hasErrors()) {
            List<State> stateList = stateAndCountryService.getAllStates();
            List<Country> countryList = stateAndCountryService.getAllCountries();
            model.addAttribute("stateList", stateList);
            model.addAttribute("countryList", countryList);
            model.addAttribute("clientId", id);

            return "createAddress";
        }

        addressService.registerAddress(client, addressDTO);

        return "redirect:/index";
    }

    @DeleteMapping("index/{id}/addresses/delete")
    public String deleteClientAddress(@PathVariable long id) {
        addressService.deleteAddress(id);

        return "redirect:/index";
    }

    @GetMapping("updateAddress/{addressId}")
    public String updateClientAddressGet(@PathVariable long addressId, Model model) {
        Address address = addressService.getAddressById(addressId);
        AddressDTO addressDTO = new AddressDTO();

        List<State> stateList = stateAndCountryService.getAllStates();
        List<Country> countryList = stateAndCountryService.getAllCountries();


        addressService.populateAddressDTO(addressDTO, address);

        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("stateList", stateList);
        model.addAttribute("countryList", countryList);

        return "updateAddress";
    }

    @PutMapping("updateAddress/{addressId}")
    public String updateClientAddressPost(@PathVariable long addressId, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult result, Model model) {
        Address addressToUpdate = addressService.getAddressById(addressId);
        List<State> stateList = stateAndCountryService.getAllStates();
        List<Country> countryList = stateAndCountryService.getAllCountries();

        if(result.hasErrors()) {
            model.addAttribute("addressId", addressId);
            model.addAttribute("stateList", stateList);
            model.addAttribute("countryList", countryList);

            return "updateAddress";
        }

        addressService.updateAddress(addressId, addressDTO);

        return "redirect:/index";
    }

    @GetMapping("index/{id}/creditCards")
    @ResponseBody
    public List<CreditCard> getClientCreditCards(@PathVariable long id) {
        return creditCardService.getClientCreditCards(id);
    }

    @GetMapping("createCreditCard/{id}")
    public String createClientCreditCardGet(@PathVariable long id, Model model) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();

        List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("clientId", id);
        model.addAttribute("creditCardBrandList", creditCardBrandList);
        //System.out.println("GET Method Id output: " + creditCardDTO.getClientId());

        return "createCreditCard";
    }


    @PostMapping("createCreditCard/{id}")
    public String createClientCreditCardPost(@PathVariable long id, @Valid @ModelAttribute CreditCardDTO creditCardDTO, BindingResult result, Model model) {
        Client client = clientService.getClientById(id);

        System.out.println("POST Method Id output: " + id);

        if(result.hasErrors()) {
            List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

            model.addAttribute("clientId", id);
            model.addAttribute("creditCardBrandList", creditCardBrandList);

            return "createCreditCard";
        }

        creditCardService.registerCreditCard(client, creditCardDTO);

        return "redirect:/index";

    }

    @DeleteMapping("index/{id}/creditCards/delete")
    public String deleteClientCreditCard(@PathVariable long id) {
        creditCardService.deleteCreditCard(id);

        return "redirect:/index";
    }


    @GetMapping("updateCreditCard/{id}")
    public String updateClientCreditCardGet(@PathVariable long id, Model model) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);
        CreditCardDTO creditCardDTO = new CreditCardDTO();

        creditCardService.populateCreditCardDTO(creditCardDTO, creditCard);

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("creditCardId", id);

        return "updateCreditCard";

    }

    @PutMapping("updateCreditCard/{id}")
    public String updateClientCreditCardPut(@PathVariable long id, @Valid @ModelAttribute CreditCardDTO creditCardDTO, BindingResult result, Model model) {
        CreditCard creditCardToUpdate = creditCardService.getCreditCardById(id);

        if(result.hasErrors()) {
            model.addAttribute("creditCardId", id);

            return "updateCreditCard";
        }

        creditCardService.updateCreditCard(creditCardToUpdate, creditCardDTO);

        return "redirect:/index";
    }

    @GetMapping("/createClient")
    public String addClientGet(Model model) {
        ClientDTO clientDTO = new ClientDTO();
        List<Gender> genderList = clientService.getAllGenders();
        List<State> stateList = stateAndCountryService.getAllStates();
        List<Country> countryList = stateAndCountryService.getAllCountries();

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("genderList", genderList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("countryList", countryList);

        return "createClient";
    }

    @PostMapping("/createClient")
    public String addClientPost(@Valid @ModelAttribute("clientDTO") ClientDTO clientDTO, BindingResult result, Model model) {

        if(result.hasErrors()) {
            List<Gender> genderList = clientService.getAllGenders();
            List<State> stateList = stateAndCountryService.getAllStates();
            List<Country> countryList = stateAndCountryService.getAllCountries();
            model.addAttribute("genderList", genderList);
            model.addAttribute("stateList", stateList);
            model.addAttribute("countryList", countryList);

            return "createClient";
        }

        Client client = new Client();

        clientService.registerClient(client, clientDTO);

        // Validação da senha
//        if (!client.isValidPassword(clientDTO.getPassword())) {
//            return "Senha inválida. A senha deve ter pelo menos 8 caracteres, incluir letras maiúsculas, minúsculas e caracteres especiais.";
//        }

        return "redirect:/index";
    }


    @GetMapping("/updateClient/{id}")
    public String updateClientGet(@PathVariable long id, Model model) {

        Client client = clientService.getClientById(id);

        ClientBasicDataDTO clientBasicDataDTO = new ClientBasicDataDTO();

        clientService.populateClientBasicDataDTO(clientBasicDataDTO, client);

        List<Gender> genderList = clientService.getAllGenders();
        model.addAttribute("clientBasicDataDTO", clientBasicDataDTO);
        model.addAttribute("genderList", genderList);
        model.addAttribute("clientId", id);

        return "updateClient";

    }

    @PutMapping("/updateClient/{id}")
    public String updateClient(@PathVariable long id, @Valid @ModelAttribute ClientBasicDataDTO clientBasicDataDTO, BindingResult result, Model model) {

        Client clientToUpdate = clientService.getClientById(id);

        if(result.hasErrors()) {
            List<Gender> genderList = clientService.getAllGenders();
            model.addAttribute("clientId", id);
            model.addAttribute("genderList", genderList);

            return "updateClient";
        }

        clientService.updateClient(clientToUpdate, clientBasicDataDTO);

        return "redirect:/index";

    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {
        clientService.deleteClientById(id);

        return "redirect:/index";
    }

}