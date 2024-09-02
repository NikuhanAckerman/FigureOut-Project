package com.project.figureout.controller;

import com.project.figureout.dto.ClientDTO;
import com.project.figureout.repository.ClientRepository;
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

    @GetMapping({"", "/", "/index"})
    public String showClients(Model model) {

        Iterable<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "index";
    }

    @GetMapping("/createClient")
    public String addClient(Model model) {
        ClientDTO clientDTO = new ClientDTO();
        model.addAttribute("clientDTO", clientDTO);
        return "createClient";
    }

    @PostMapping("/createClient")
    public String addClientPost(@ModelAttribute("clientDTO") ClientDTO clientDTO) {

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        //client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setCpf(clientDTO.getCpf());
        //client.setAddress(clientDTO.getAddress());
        client.setDisabled(clientDTO.isDisabled());

        clientRepository.save(client);

        return "redirect:/index";
    }

    @GetMapping("/updateClient/{id}")
    public String showSpecificClient(@PathVariable long id, Model model) {
        Optional<Client> client = clientRepository.findById(id);

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName(client.get().getName());
        clientDTO.setEmail(client.get().getEmail());
        //clientDTO.setPhoneNumber(client.get().getPhoneNumber());
        clientDTO.setCpf(client.get().getCpf());
        //clientDTO.setAddress(client.get().getAddress());
        clientDTO.setDisabled(client.get().isDisabled());

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("clientID", id);

        return "updateClient";
    }

    @PutMapping("/updateClient/{id}")
    public String updateClient(@PathVariable long id, @ModelAttribute ClientDTO clientDTO) {
        Optional<Client> clientToChange = clientRepository.findById(id);

        if(clientToChange.isPresent()) {
            Client client = clientToChange.get();

            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            //client.setPhoneNumber(clientDTO.getPhoneNumber());
            client.setCpf(clientDTO.getCpf());
            //client.setAddress(clientDTO.getAddress());
            client.setDisabled(clientDTO.isDisabled());
            clientRepository.save(client);
        }

        return "redirect:/index";
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {

        clientRepository.deleteById(id);

        return "redirect:/index";
    }



}
