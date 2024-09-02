package com.project.figureout.controller;

import com.project.figureout.dto.AddressDTO;
import com.project.figureout.model.Address;
import com.project.figureout.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping({"", "/", "/index"})
    public String showAddresses(Model model) {

        Iterable<Address> addresses =  addressRepository.findAll();
        model.addAttribute("address", addresses);
        return "index";
    }

    @GetMapping("/createClient")
    public String addAddress(Model model) {
        AddressDTO addressDTO = new AddressDTO();
        model.addAttribute("addressDTO", addressDTO);
        return "createClient";
    }

    @PostMapping("/createClient")
    public String addAddressPost(@ModelAttribute("addressDTO") AddressDTO addressDTO) {

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

        return "redirect:/index";
    }

    @GetMapping("/updateClient/{id}")
    public String showSpecificAddress(@PathVariable long id, Model model) {
        Optional<Address> address = addressRepository.findById(id);

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


        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("addressID", id);

        return "updateClient";
    }

    @PutMapping("/updateClient/{id}")
    public String updateAddress(@PathVariable long id, @ModelAttribute AddressDTO addressDTO) {
        Optional<Address> addressToChange = addressRepository.findById(id);

        if(addressToChange.isPresent()) {
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

            addressRepository.save(address);
        }

        return "redirect:/index";
    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {

        addressRepository.deleteById(id);

        return "redirect:/index";
    }



}
