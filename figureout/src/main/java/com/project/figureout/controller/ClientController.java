package com.project.figureout.controller;

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

    @PostMapping("/criarCliente")
    public Client addClient(@RequestBody Client client) {
        clientRepository.save(client);
        return client;
    }

    @GetMapping({"", "/"})
    public String getAllClients(Model model) {

        Iterable<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "index";
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable long id) {
        return clientRepository.findById(id);
    }

    @PutMapping("/atualizarCliente")
    public Client updateClient(@PathVariable long id, @RequestBody Client client) {
        Client clientToChange = getClientById(id).get();

        clientToChange.setName(client.getName());
        clientToChange.setEmail(client.getEmail());
        clientToChange.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(clientToChange);
        return clientToChange;
    }

}
