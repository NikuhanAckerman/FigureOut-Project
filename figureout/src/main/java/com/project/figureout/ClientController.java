package com.project.figureout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.figureout.model.Client;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping()
    public Client addClient(@RequestBody Client client) {
        clientRepository.save(client);
        return client;
    }

    @GetMapping()
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }


}
