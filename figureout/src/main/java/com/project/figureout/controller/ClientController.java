package com.project.figureout.controller;

import com.project.figureout.dto.AddressDTO;
import com.project.figureout.dto.ClientDTO;
import com.project.figureout.dto.PhoneDTO;
import com.project.figureout.model.Address;
import com.project.figureout.model.Phone;
import com.project.figureout.repository.AddressRepository;
import com.project.figureout.repository.ClientRepository;
import com.project.figureout.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.figureout.model.Client;

import java.util.Optional;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    private AddressRepository addressRepository;
    private PhoneRepository phoneRepository;

    @GetMapping({"", "/", "/index"})
    public String showClients(Model model) {

        Iterable<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);

        Iterable<Address> addresses =  addressRepository.findAll();
        model.addAttribute("addresses", addresses);

        Iterable<Phone> phones =  phoneRepository.findAll();
        model.addAttribute("phones", phones);
        return "index";
    }

    @GetMapping("/createClient")
    public String addClient(Model model) {
        ClientDTO clientDTO = new ClientDTO();
        AddressDTO addressDTO = new AddressDTO();
        PhoneDTO phoneDTO = new PhoneDTO();

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("phoneDTO", phoneDTO);
        return "createClient";
    }

    @PostMapping("/createClient")
    public String addClientPost(@ModelAttribute("clientDTO") ClientDTO clientDTO,
                                @ModelAttribute("addressDTO") AddressDTO addressDTO,
                                @ModelAttribute("phoneDTO") PhoneDTO phoneDTO) {

        // Atributos de cliente.
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setCpf(clientDTO.getCpf());
        client.setDisabled(clientDTO.isDisabled());

        clientRepository.save(client);

        // Atributos de endereço.
        Address address = new Address();
        address.setTypeOfResidence(addressDTO.getTypeOfResidence());
        address.setAdressing(addressDTO.getAdressing());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setNeighbourhood(addressDTO.getNeighbourhood());
        address.setAddressingType(addressDTO.getAddressingType());
        address.setCep(addressDTO.getCep());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setObservation(addressDTO.getObservation());

        addressRepository.save(address);

        // Atributos de telefone.
        Phone phone = new Phone();
        phone.setCellphone(phoneDTO.isCellphone());
        phone.setDdd(phoneDTO.getDdd());
        phone.setPhoneNumber(phoneDTO.getPhoneNumber());

        phoneRepository.save(phone);

        return "redirect:/index";
    }

    @GetMapping("/updateClient/{id}")
    public String showSpecificClient(@PathVariable long id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        Optional<Address> address = addressRepository.findById(id);
        Optional<Phone> phone = phoneRepository.findById(id);

        //Atributos de cliente
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.get().getName());
        clientDTO.setEmail(client.get().getEmail());
        clientDTO.setCpf(client.get().getCpf());
        clientDTO.setDisabled(client.get().isDisabled());

        //Atributos de endereço.
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setTypeOfResidence(address.get().getTypeOfResidence());
        addressDTO.setAdressing(address.get().getAdressing());
        addressDTO.setHouseNumber(address.get().getHouseNumber());
        addressDTO.setNeighbourhood(address.get().getNeighbourhood());
        addressDTO.setAddressingType(address.get().getAddressingType());
        addressDTO.setCep(address.get().getCep());
        addressDTO.setCity(address.get().getCity());
        addressDTO.setState(address.get().getState());
        addressDTO.setCountry(address.get().getCountry());
        addressDTO.setObservation(address.get().getObservation());

        // Atributos de telefone.
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setCellphone(phone.get().isCellphone());
        phoneDTO.setDdd(phone.get().getDdd());
        phoneDTO.setPhoneNumber(phone.get().getPhoneNumber());

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("clientID", id);
        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("addressID", id);
        model.addAttribute("phoneDTO", phoneDTO);
        model.addAttribute("phoneID", id);

        return "updateClient";
    }

    @PutMapping("/updateClient/{id}")
    public String updateClient(@PathVariable long id, @ModelAttribute ClientDTO clientDTO,
                               @ModelAttribute AddressDTO addressDTO,
                               @ModelAttribute PhoneDTO phoneDTO) {
        Optional<Client> clientToChange = clientRepository.findById(id);
        Optional<Address> addressToChange = addressRepository.findById(id);
        Optional<Phone> phoneToChange = phoneRepository.findById(id);


        if(clientToChange.isPresent()) {
            //Atributos de cliente
            Client client = clientToChange.get();
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setCpf(clientDTO.getCpf());
            client.setDisabled(clientDTO.isDisabled());

            //Atributos de endereço.
            Address address = addressToChange.get();
            address.setTypeOfResidence(addressDTO.getTypeOfResidence());
            address.setAdressing(addressDTO.getAdressing());
            address.setHouseNumber(addressDTO.getHouseNumber());
            address.setNeighbourhood(addressDTO.getNeighbourhood());
            address.setAddressingType(addressDTO.getAddressingType());
            address.setCep(addressDTO.getCep());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setCountry(addressDTO.getCountry());
            address.setObservation(addressDTO.getObservation());

            // Atributos de telefone.
            Phone phone = phoneToChange.get();
            phone.setCellphone(phoneDTO.isCellphone());
            phone.setDdd(phoneDTO.getDdd());
            phone.setPhoneNumber(phoneDTO.getPhoneNumber());

            clientRepository.save(client);
            addressRepository.save(address);
            phoneRepository.save(phone);
        }

        return "redirect:/index";
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {

        clientRepository.deleteById(id);
        addressRepository.deleteById(id);
        phoneRepository.deleteById(id);

        return "redirect:/index";
    }



}
