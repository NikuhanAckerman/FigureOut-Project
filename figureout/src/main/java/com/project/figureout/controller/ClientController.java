package com.project.figureout.controller;

import com.project.figureout.dto.AddressDTO;
import com.project.figureout.dto.ClientDTO;
import com.project.figureout.dto.GenderDTO;
import com.project.figureout.dto.PhoneDTO;
import com.project.figureout.model.Address;
import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import com.project.figureout.repository.AddressRepository;
import com.project.figureout.repository.ClientRepository;
import com.project.figureout.repository.GenderRepository;
import com.project.figureout.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.figureout.model.Client;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private GenderRepository genderRepository;

    @GetMapping({"", "/", "/index"})
    public String showClientsGet(Model model) {

        //List<Gender> genders =  genderRepository.findAll();
        //model.addAttribute("gender", genders);

        List<Client> clients =  clientRepository.findAll();
        model.addAttribute("clients", clients);

        //List<Address> addresses =  addressRepository.findAll();
        //model.addAttribute("address", addresses);

        //List<Phone> phones =  phoneRepository.findAll();
        //model.addAttribute("phone", phones);
        return "index";
    }



    @GetMapping("/createClient")
    public String addClientGet(Model model) {
        ClientDTO clientDTO = new ClientDTO();

        List<Gender> genderList = genderRepository.findAll();

        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("genderList", genderList);

        return "createClient";
    }

    @PostMapping("/createClient")
    public String addClientPost(@ModelAttribute("clientDTO") ClientDTO clientDTO,/*, @RequestParam String password*/ Model model) {

        Gender gender = genderRepository.findById(clientDTO.getGender().getId()).orElse(null);

        // Atributos de telefone.
        Phone phone = new Phone();
        phone.setCellphone(clientDTO.getPhone().isCellphone());
        phone.setDdd(clientDTO.getPhone().getDdd());
        phone.setPhoneNumber(clientDTO.getPhone().getPhoneNumber());

        phoneRepository.save(phone);

        // Atributos de endereço.

        Client client = new Client();
        client.setPhone(phone);
        client.setGender(gender);
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setCpf(clientDTO.getCpf());
        client.setBirthday(clientDTO.getBirthday());
        client.setEnabled(clientDTO.isEnabled());
        client.setPassword(clientDTO.getPassword());

        Address address = new Address();
        address.setNickname(clientDTO.getAddress().getNickname());
        address.setTypeOfResidence(clientDTO.getAddress().getTypeOfResidence());
        address.setAddressing(clientDTO.getAddress().getAddressing());
        address.setHouseNumber(clientDTO.getAddress().getHouseNumber());
        address.setNeighbourhood(clientDTO.getAddress().getNeighbourhood());
        address.setAddressingType(clientDTO.getAddress().getAddressingType());
        address.setCep(clientDTO.getAddress().getCep());
        address.setCity(clientDTO.getAddress().getCity());
        address.setState(clientDTO.getAddress().getState());
        address.setCountry(clientDTO.getAddress().getCountry());
        address.setObservation(clientDTO.getAddress().getObservation());
        addressRepository.save(address);

        client.addAddress(address);

        clientRepository.save(client);

        // Validação da senha
//        if (!client.isValidPassword(clientDTO.getPassword())) {
//            return "Senha inválida. A senha deve ter pelo menos 8 caracteres, incluir letras maiúsculas, minúsculas e caracteres especiais.";
//        }

        return "redirect:/index";
    }

    /*
    @GetMapping("/clientAddresses/{id}")
    public String showClientAddresses(@PathVariable long id, Model model) {
        List<Address> clientAddressList = clientRepository.findById(id).get().getAddresses();

        model.addAttribute("clientAddressList", clientAddressList);

        return "clientAddresses";
    }

     */


    @GetMapping("/updateClient/{id}")
    public String showSpecificClient(@PathVariable long id, Model model) {
       /* Optional<Client> client = clientRepository.findById(id);
        Optional<Address> address = addressRepository.findById(id);
        Optional<Phone> phone = phoneRepository.findById(id);
        Optional<Gender> gender = genderRepository.findById(id);

        //Atributo de gênero
        GenderDTO genderDTO = new GenderDTO();
        genderDTO.setGender((gender.get().getGender()));

        //Atributos de cliente
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.get().getName());
        clientDTO.setEmail(client.get().getEmail());
        clientDTO.setPassword(client.get().getPassword());
        clientDTO.setCpf(client.get().getCpf());
        clientDTO.setBirthday(client.get().getBirthday());
        clientDTO.setEnabled(client.get().isEnabled());

        //Atributos de endereço.
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setTypeOfResidence(address.get().getTypeOfResidence());
        addressDTO.setAddressing(address.get().getAdressing());
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

        model.addAttribute("genderDTO", genderDTO);
        model.addAttribute("genderID", id);
        model.addAttribute("clientDTO", clientDTO);
        model.addAttribute("clientID", id);
        model.addAttribute("addressDTO", addressDTO);
        model.addAttribute("addressID", id);
        model.addAttribute("phoneDTO", phoneDTO);
        model.addAttribute("phoneID", id);
        */
        return "updateClient";


    }

    @PutMapping("/updateClient/{id}")
    public String updateClient(@PathVariable long id, @ModelAttribute ClientDTO clientDTO,
                               @ModelAttribute AddressDTO addressDTO,
                               @ModelAttribute PhoneDTO phoneDTO,
                               @ModelAttribute GenderDTO genderDTO) {
        /*
        Optional<Client> clientToChange = clientRepository.findById(id);
        Optional<Address> addressToChange = addressRepository.findById(id);
        Optional<Phone> phoneToChange = phoneRepository.findById(id);
        Optional<Gender> genderToChange = genderRepository.findById(id);


        if(clientToChange.isPresent()) {
            //Atributo de gênero
            Gender gender = genderToChange.get();
            gender.setGender(genderDTO.getGender());

            //Atributos de cliente
            Client client = clientToChange.get();
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setPassword(clientDTO.getPassword());
            client.setCpf(clientDTO.getCpf());
            client.setBirthday(clientDTO.getBirthday());
            client.setEnabled(clientDTO.isEnabled());

            //Atributos de endereço.
            Address address = addressToChange.get();
            address.setTypeOfResidence(addressDTO.getTypeOfResidence());
            address.setAdressing(addressDTO.getAddressing());
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

            genderRepository.save(gender);
            clientRepository.save(client);
            addressRepository.save(address);
            phoneRepository.save(phone);
        }
        */
        return "redirect:/index";


    }

    @DeleteMapping("/deleteClient/{id}")
    public String deleteClient(@PathVariable long id) {
        /*
        genderRepository.deleteById(id);
        clientRepository.deleteById(id);
        addressRepository.deleteById(id);
        phoneRepository.deleteById(id);
        */
        return "redirect:/index";
    }



}
