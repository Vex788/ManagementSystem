package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    ArrayList<Admin> findAll();

    Admin findByLogin(String login);

    boolean existsByLogin(String login);
}
