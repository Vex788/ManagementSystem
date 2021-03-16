package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    ArrayList<Subscription> findAll();
}