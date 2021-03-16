package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    ArrayList<PaymentHistory> findAll();
}