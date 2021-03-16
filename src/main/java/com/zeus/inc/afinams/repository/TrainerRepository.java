package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    ArrayList<Trainer> findAll();

    Trainer findByLogin(String login);

    boolean existsByLogin(String login);
}
