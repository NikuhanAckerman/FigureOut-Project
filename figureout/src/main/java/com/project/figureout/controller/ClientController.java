package com.project.figureout.controller;

import com.project.figureout.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.figureout.model.Client;

import java.util.Optional;

@RestController
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

}
