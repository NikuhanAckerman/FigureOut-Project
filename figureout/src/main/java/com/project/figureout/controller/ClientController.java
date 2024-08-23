package com.project.figureout.controller;

import com.project.figureout.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.project.figureout.model.Client;

import java.util.Optional;

@Controller
@RequestMapping(path = "/clientes")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping()
    public Client addClient(@RequestBody Client client) {
        clientRepository.save(client);
        return client;
    }

    @GetMapping({"", "/"})
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable long id) {
        return clientRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable long id, @RequestBody Client client) {
        Optional<Client> clientToChange = getClientById(id);

        client.setName(clientToChange.get().getName());

    }

}
