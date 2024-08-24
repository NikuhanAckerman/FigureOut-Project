package com.project.figureout.controller;

import com.project.figureout.dto.ClientDTO;
import com.project.figureout.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String handleCreateClient(@ModelAttribute("clientDTO") ClientDTO clientDTO, BindingResult bindingResult) {


        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setCpf(clientDTO.getCpf());
        client.setAddress(clientDTO.getAddress());

        System.out.println("post called");

        return "redirect:/index";

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
