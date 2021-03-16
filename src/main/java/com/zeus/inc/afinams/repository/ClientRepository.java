package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    ArrayList<Client> findAll();

    Client findByLogin(String login);

    boolean existsByLogin(String login);
}
