package com.project.figureout.repository;

import com.project.figureout.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    //List<Client> findByNameContainingIgnoreCase(String name);
    //List<Client> findByEmailContainingIgnoreCase(String email);
    //List<Client> findByPasswordContainingIgnoreCase(String password);
    //List<Client> findByCpfContainingIgnoreCase(String cpf);
    //Optional<Client> findById(Long id);
}
