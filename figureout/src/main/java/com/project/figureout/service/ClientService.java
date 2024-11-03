package com.project.figureout.service;

import com.project.figureout.dto.*;
import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private SaleService saleService;

    private HashMap<Long, BigDecimal> clientAndSaleTotals = new HashMap<>();

    // Client Methods

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    private void populateClientsAndSalesMap() {
        List<Client> allClients = getAllClients();

        if(!clientAndSaleTotals.isEmpty()) {
            clientAndSaleTotals.clear();
        }

        for(Client currentClient : allClients) {

            List<Sale> clientSaleList = new ArrayList<>();
            clientSaleList.addAll(saleService.getClientSalesByClientId(currentClient.getId()));

            BigDecimal salesTotals = BigDecimal.valueOf(0.00);

            for(Sale currentSale: clientSaleList) {
                salesTotals = salesTotals.add(currentSale.getFinalPrice());
            }

            clientAndSaleTotals.put(currentClient.getId(), salesTotals);

        }

    }

    public Client getClientById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com base no ID."));
    }

    public void deleteClientById(long id) {
        clientRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveClient(Client client) {

        clientRepository.save(client);

    }

    public void registerClient(Client client, ClientDTO clientDTO) {

        clientBasicDataSetter(client, clientDTO.getClientBasicDataDTO());

        saveClient(client);

        cartService.changeClientCart(client);

        AddressDTO clientDTOAddress = clientDTO.getAddressDTO();
        addressService.registerAddress(client, clientDTOAddress);
    }

    public void updateClient(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        clientBasicDataSetter(client, clientBasicDataDTO);

        saveClient(client);
    }

    // Serviço para mudar a senha.
    public boolean changePassword(Long id, ClientChangePasswordDTO changePasswordDTO) {
        // Pegar o usuário pelo ID.
        Client client = getClientById(id);

        // Checar se a velha senha bate.
        if (client != null && client.getPassword().equals(changePasswordDTO.getOldPassword())) {
            System.out.println("nao é nulo e senha igual a senha velha do dto");
            // Checar se a nova senha e a confimação batem.
            if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                // Atualizar a senha.
                client.setPassword(changePasswordDTO.getNewPassword());
                saveClient(client);
                return true;
            }
        }

        return false;
    }

    public List<Address> getClientAddresses(long id) {
        return getClientById(id).getAddresses();
    }

    public List<CreditCard> getClientCreditCards(long id) {
        return getClientById(id).getCreditCards();
    }

    public void clientBasicDataSetter(Client client, ClientBasicDataDTO clientBasicDataDTO) {
        System.out.println("calling basic data setter");
        client.setName(clientBasicDataDTO.getName());
        client.setEmail(clientBasicDataDTO.getEmail());
        client.setCpf(treatMaskedCpf(clientBasicDataDTO.getCpf()));
        client.setPassword(clientBasicDataDTO.getPassword());
        client.setBirthday(clientBasicDataDTO.getBirthday());
        client.setEnabled(clientBasicDataDTO.isEnabled());

        Gender gender = new Gender();
        Phone phone = new Phone();

        gender.setId(clientBasicDataDTO.getGenderDTO().getId());

        phone.setCellphone(clientBasicDataDTO.getPhoneDTO().isCellphone());
        phone.setDdd(clientBasicDataDTO.getPhoneDTO().getDdd());
        phone.setPhoneNumber(clientBasicDataDTO.getPhoneDTO().getPhoneNumber());

        client.setGender(gender);
        client.setPhone(phone);

        client.setRanking(0);
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    public void populateClientBasicDataDTO(ClientBasicDataDTO clientBasicDataDTO, Client client) {

        clientBasicDataDTO.setName(client.getName());
        clientBasicDataDTO.setEmail(client.getEmail());
        clientBasicDataDTO.setCpf(addMaskToCpf(client.getCpf()));
        clientBasicDataDTO.setPassword(client.getPassword());
        clientBasicDataDTO.setBirthday(client.getBirthday());
        clientBasicDataDTO.setEnabled(client.isEnabled());

        clientBasicDataDTO.getGenderDTO().setId(client.getGender().getId());

        clientBasicDataDTO.getPhoneDTO().setCellphone(client.getPhone().isCellphone());
        clientBasicDataDTO.getPhoneDTO().setDdd(client.getPhone().getDdd());
        clientBasicDataDTO.getPhoneDTO().setPhoneNumber(client.getPhone().getPhoneNumber());
    }

    public String treatMaskedCpf(String cpf) {
        String treatedCpf;

        treatedCpf = cpf.replace(".", "");

        treatedCpf = treatedCpf.replace("-", "");

        return treatedCpf;

    }

    public String addMaskToCpf(String cpf) {
        // 123.456.789-00
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }


    // Método para filtrar os atributos do cliente.
    public List<Client> filterClients(String name, String email, String password, String cpf, Long id) {
        List<Client> clients = getAllClients();

        if (id != null && id > 0) {
            clients = clients.stream()
                    .filter(client -> client.getId() == id) // Comparação direta
                    .collect(Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            clients = clients.stream()
                    .filter(client -> client.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (email != null && !email.isEmpty()) {
            clients = clients.stream()
                    .filter(client -> client.getEmail().toLowerCase().contains(email.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (password != null && !password.isEmpty()) {
            clients = clients.stream()
                    .filter(client -> client.getPassword().toLowerCase().contains(password.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (cpf != null && !cpf.isEmpty()) {
            clients = clients.stream()
                    .filter(client -> client.getCpf().toLowerCase().contains(cpf.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return clients.isEmpty() ? new ArrayList<>() : clients; // Retornar uma lista vazia se nenhum filtro bater
    }

    public void recalculateClientRanking() {

        populateClientsAndSalesMap();

        List<Map.Entry<Long, BigDecimal>> sortedClientsBySales = clientAndSaleTotals.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, BigDecimal>comparingByValue().reversed()) // Sort by value, descending
                .toList();

        System.out.println("HashMap of total sale values ordered by ranking:");

        int rank = 1;
        for (Map.Entry<Long, BigDecimal> entry : sortedClientsBySales) {
            Long clientId = entry.getKey();
            BigDecimal totalSales = entry.getValue();

            Client client = getClientById(clientId);
            client.setRanking(rank);
            saveClient(client);

            System.out.println("Rank " + rank + ": Client " + getClientById(clientId).getName() +
                    " - Total Sales: $" + totalSales);
            rank++;
        }



        /*List<BigDecimal> totalSaleValues = new ArrayList<>(clientAndSaleTotals.values());
        Collections.sort(totalSaleValues);
        totalSaleValues = totalSaleValues.reversed();

        System.out.println("HashMap de valores totais da venda:");

        clientAndSaleTotals.forEach((key, value) -> {
            System.out.println("Cliente: " + getClientById(key).getName());
            System.out.println("Valor total gasto em vendas: R$" + value);
        });

        System.out.println("Lista com valores ordenados:");

        int i = 1;

        for(BigDecimal currentValue : totalSaleValues) {
            System.out.println("Valor: R$" + currentValue + " Ranking: " + i);
            i += 1;
        }*/



        /*
        System.out.println("CALLING RECALCULATE CLIENT RANKING");

        HashMap<Long, BigDecimal> clientTotalAmountSales = new HashMap<>();
        List<BigDecimal> totalSales = new ArrayList<>();
        LinkedHashMap<Long, BigDecimal> sortedSales = new LinkedHashMap<>();

        long idPreviousClient = 0;

        for (Map.Entry<Long, List<Sale>> entry : clientsAndSales.entrySet()) {
            long currentClientId = entry.getKey();
            List<Sale> listOfSales = entry.getValue();

            BigDecimal totalAmountInTheListOfSales = BigDecimal.valueOf(0.00);
            idPreviousClient = currentClientId;

            for (Sale currentSale : listOfSales) {
                totalAmountInTheListOfSales = totalAmountInTheListOfSales.add(currentSale.getFinalPrice());
                clientTotalAmountSales.put(currentClientId, totalAmountInTheListOfSales);
                totalSales.add(totalAmountInTheListOfSales);
            }
        }

        totalSales.sort(new Comparator<BigDecimal>() {
            public int compare(BigDecimal bd1, BigDecimal bd2) {
                return bd1.compareTo(bd2);
            }
        });

        for (BigDecimal bd : totalSales) {
            for (Map.Entry<Long, BigDecimal> entry : clientTotalAmountSales.entrySet()) {
                if (entry.getValue().equals(bd)) {
                    sortedSales.put(entry.getKey(), bd);
                }
            }
        }

        int i = 1;

        for (Map.Entry<Long, BigDecimal> entry : sortedSales.entrySet()) {
            long currentClientId = entry.getKey();
            BigDecimal totalInSales = entry.getValue();
            System.out.println("");
            System.out.println("Cliente: " + getClientById(currentClientId).getName());
            System.out.println("Quantidade total gasta com compras: R$" + totalInSales);
            Client client = getClientById(currentClientId);
            client.setRanking(i);
            System.out.println("Ranking do cliente: " + i);
            saveClient(client);
            i += 1;
            System.out.println("");
        }
        */
    }

}
