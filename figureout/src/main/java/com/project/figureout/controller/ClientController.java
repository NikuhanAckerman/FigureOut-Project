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
@RequestMapping(path = "/index")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"", "/"})
    public String showClients(Model model) {

        Iterable<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "index";
    }

    @PostMapping("/createClient")
    public String addClient(Model model) {
        ClientDTO clientDTO = new ClientDTO();
        model.addAttribute("clientDTO", clientDTO);
        return "index/createClient";

    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable long id) {
        return clientRepository.findById(id);
    }

    @PutMapping("/updateClient")
    public Client updateClient(@PathVariable long id, @RequestBody Client client) {
        Client clientToChange = getClientById(id).get();

        clientToChange.setName(client.getName());
        clientToChange.setEmail(client.getEmail());
        clientToChange.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(clientToChange);
        return clientToChange;
    }


}
