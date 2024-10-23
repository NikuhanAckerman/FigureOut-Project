package com.project.figureout.controller;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.SalesCardsRepository;
import com.project.figureout.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SaleService saleService;

    @Autowired
    private SalesCardsRepository salesCardsRepository;

    @GetMapping("/showAllClients")
    public String showClientsGet(Model model) {

        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);

        return "showClients";
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

        return "redirect:/showAllClients";
    }

    @DeleteMapping("index/{id}/addresses/delete")
    public String deleteClientAddress(@PathVariable long id) {
        addressService.deleteAddress(id);

        return "redirect:/showAllClients";
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

        return "redirect:/showAllClients";
    }

    @GetMapping("index/{id}/creditCards")
    @ResponseBody
    public List<CreditCard> getClientCreditCards(@PathVariable long id) {
        return creditCardService.getClientCreditCards(id);
    }

    @GetMapping("createCreditCard/{id}")
    public String createClientCreditCardGet(@PathVariable long id, Model model) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setClientId(id);

        List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

         // necessary to pre-set this in the form via hidden field
        //System.out.println("GET METHOD: " + creditCardDTO.getClientId());

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("clientId", id);
        model.addAttribute("creditCardBrandList", creditCardBrandList);

        return "createCreditCard";
    }

    @PostMapping("createCreditCard/{id}")
    public String createClientCreditCardPost(@PathVariable long id, @Valid @ModelAttribute CreditCardDTO creditCardDTO, BindingResult result, Model model) {
        Client client = clientService.getClientById(id);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";

        if(result.hasErrors()) {
            List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

            System.out.println(ANSI_RED + "there are errors in credit card creation" + ANSI_RESET);

            model.addAttribute("creditCardDTO", creditCardDTO);
            model.addAttribute("clientId", id);
            model.addAttribute("creditCardBrandList", creditCardBrandList);

            return "createCreditCard";
        }

        System.out.println(ANSI_RED + "no errors in credit card creation" + ANSI_RESET);

        creditCardService.registerCreditCard(client, creditCardDTO);

        return "redirect:/showAllClients";

    }

    @DeleteMapping("index/{id}/creditCards/delete")
    public String deleteClientCreditCard(@PathVariable long id) {
        creditCardService.deleteCreditCard(id);

        return "redirect:/showAllClients";
    }

    @GetMapping("updateCreditCard/{id}")
    public String updateClientCreditCardGet(@PathVariable long id, Model model) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);
        Client client = creditCard.getClient();

        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setClientId(client.getId());
        creditCardDTO.setCreditCardId(creditCard.getId());

        List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

        creditCardService.populateCreditCardDTO(creditCardDTO, creditCard);

        model.addAttribute("creditCardDTO", creditCardDTO);
        model.addAttribute("creditCardBrandList", creditCardBrandList);
        model.addAttribute("creditCardId", id);

        return "updateCreditCard";
    }

    @PutMapping("updateCreditCard/{id}")
    public String updateClientCreditCardPut(@PathVariable long id, @Valid @ModelAttribute CreditCardDTO creditCardDTO, BindingResult result, Model model) {
        CreditCard creditCardToUpdate = creditCardService.getCreditCardById(id);
        List<CreditCardBrand> creditCardBrandList = creditCardService.getAllCreditCardBrands();

        if(result.hasErrors()) {
            model.addAttribute("creditCardId", id);
            model.addAttribute("creditCardBrandList", creditCardBrandList);

            return "updateCreditCard";
        }

        creditCardService.updateCreditCard(creditCardToUpdate, creditCardDTO);

        return "redirect:/showAllClients";
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

        Client client = new Client();

        if(result.hasErrors()) {
            List<Gender> genderList = clientService.getAllGenders();
            List<State> stateList = stateAndCountryService.getAllStates();
            List<Country> countryList = stateAndCountryService.getAllCountries();
            model.addAttribute("genderList", genderList);
            model.addAttribute("stateList", stateList);
            model.addAttribute("countryList", countryList);
            model.addAttribute("clientId", client.getId());

            return "createClient";
        }

        clientService.registerClient(client, clientDTO);

        return "redirect:/showAllClients";
    }

    // Método no controller pra página de trocar de senha.
    @GetMapping("/changePassword/{clientId}")
    public String showChangePasswordForm(@ModelAttribute ClientChangePasswordDTO changePasswordDTO,
                                         @PathVariable Long clientId,
                                         Model model) {
        model.addAttribute("changePasswordDTO", new ClientChangePasswordDTO());
        model.addAttribute("clientId", clientId);

        return "changePassword"; // Template do thymeleaf
    }

    @PostMapping("/changePassword/{clientId}")
    public String changePassword(@ModelAttribute ClientChangePasswordDTO changePasswordDTO,
                                 @PathVariable Long clientId,
                                 Model model) {
        // Chamar o serviço para mudar a senha.
        clientService.changePassword(clientId, changePasswordDTO);
        //boolean success =
        return "redirect:/showAllClients";

        /*if (!success) {
            model.addAttribute("error", "Password change failed. Please check your inputs.");
            return "changePassword"; // Mostrar o formulário de novo com um erro
        }*/

        /*model.addAttribute("message", "Password changed successfully!");
        return "/changePassword/" + clientId; // Redirecionar para uma página de sucesso.

         */
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

        return "redirect:/showAllClients";

    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {
        clientService.deleteClientById(id);

        return "redirect:/showAllClients";
    }


    // Filtro de clientes no CRUD de clientes
    @GetMapping("/clients")
    public String getClients(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) String cpf,
                             @RequestParam(required = false) Long id,
                             Model model) {
        List<Client> clients = clientService.filterClients(name, email, password, cpf, id);
        model.addAttribute("clients", clients);
        model.addAttribute("filterName", name);
        model.addAttribute("filterEmail", email);
        model.addAttribute("filterPassword", password);
        model.addAttribute("filterCpf", cpf);
        model.addAttribute("filterId", id);
        return "showClients";
    }

    @GetMapping("/clientProfileGeneral/{id}")
    public String seeClientProfile(@PathVariable long id, Model model) {
        Client client = clientService.getClientById(id);

        model.addAttribute("id", id);
        model.addAttribute("name", client.getName());
        model.addAttribute("email", client.getEmail());
        model.addAttribute("password", client.getPassword());
        model.addAttribute("gender", client.getGender());
        model.addAttribute("cpf", client.getCpf());
        model.addAttribute("phone", client.getPhone());

        return "clientProfile";
    }

    @GetMapping("/clientProfilePurchases/{id}")
    public String seeClientProfilePurchases(@PathVariable long id, Model model) {
        List<Sale> clientSales = saleService.getClientSalesByClientId(id);

        model.addAttribute("id", id);
        model.addAttribute("sales", clientSales);

        model.addAttribute("saleStatus", SaleStatusEnum.values());

        return "clientProfilePurchases";
    }

    @GetMapping("/clientProfileAddresses/{id}")
    public String seeClientProfileAddresses(@PathVariable long id, Model model) {
        List<Address> clientAddresses = clientService.getClientAddresses(id);

        model.addAttribute("id", id);
        model.addAttribute("addresses", clientAddresses);

        return "clientProfileAddresses";

    }

    @GetMapping("/clientProfileCreditCards/{id}")
    public String seeClientProfileCreditCards(@PathVariable long id, Model model) {
        List<CreditCard> clientCreditCards = clientService.getClientCreditCards(id);

        model.addAttribute("id", id);
        model.addAttribute("creditCards", clientCreditCards);

        return "clientProfileCreditCards";

    }





}