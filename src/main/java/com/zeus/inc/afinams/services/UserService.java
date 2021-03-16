package com.zeus.inc.afinams.services;

import com.zeus.inc.afinams.model.*;

import java.util.List;

public interface UserService {
    // --- AuthUser ---
    List<AuthUser> getAllAuthUsers();

    AuthUser getAuthUserById(Long id);

    AuthUser getAuthUserByLogin(String login);

    boolean existsAuthUserByLogin(String login);

    boolean save(AuthUser user);

    boolean update(AuthUser user);

    boolean delete(AuthUser user);

    // --- Trainer ---
    List<Trainer> getAllTrainers();

    Trainer getTrainerById(Long id);

    Trainer getTrainerByLogin(String login);

    boolean existsTrainerByLogin(String login);

    boolean save(Trainer trainer);

    boolean update(Trainer trainer);

    boolean delete(Trainer trainer);

    // --- Client ---
    List<Client> getAllClients();

    Client getClientById(Long id);

    Client getClientByLogin(String login);

    boolean existsClientByLogin(String login);

    boolean save(Client client);

    boolean update(Client client);

    boolean delete(Client client);

    // --- Admin ---
    List<Admin> getAllAdmins();

    Admin getAdminById(Long id);

    Admin getAdminByLogin(String login);

    boolean existsAdminByLogin(String login);

    boolean save(Admin admin);

    boolean update(Admin admin);

    boolean delete(Admin admin);

    // --- TrainingSchedule ---
    List<TrainingSchedule> getAllTrainingSchedules();

    TrainingSchedule getTrainingScheduleById(Long id);

    boolean save(TrainingSchedule schedule);

    boolean update(TrainingSchedule schedule);

    boolean delete(TrainingSchedule schedule);

    // --- Subscription ---
    List<Subscription> getAllSubscriptions();

    Subscription getSubscriptionById(Long id);

    boolean save(Subscription subscription);

    boolean update(Subscription subscription);

    boolean delete(Subscription subscription);

    // --- Subscription ---
    List<PaymentHistory> getAllPaymentHistories();

    PaymentHistory getPaymentHistoryById(Long id);

    boolean save(PaymentHistory paymentHistory);

    boolean update(PaymentHistory paymentHistory);

    boolean delete(PaymentHistory paymentHistory);
}
