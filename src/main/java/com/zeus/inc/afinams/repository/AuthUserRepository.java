package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    ArrayList<AuthUser> findAll();

    AuthUser findByLogin(String login);

    boolean existsByLogin(String login);
}
