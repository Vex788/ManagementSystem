package com.zeus.inc.afinams.services;

import com.zeus.inc.afinams.model.*;
import com.zeus.inc.afinams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    //@Transactional
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainingScheduleRepository scheduleRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    // --- CUser ---   
    @Override
    public List<AuthUser> getAllAuthUsers() {
        return authUserRepository.findAll();
    }

    @Override
    public AuthUser getAuthUserById(Long id) {
        AuthUser user = null;

        if (id != null && id > 0L) {
            user = authUserRepository.findById(id).get();
        }

        return user;
    }

    @Override
    public AuthUser getAuthUserByLogin(String login) {
        AuthUser user = null;

        if (login != null && login != "") {
            user = authUserRepository.findByLogin(login);
        }

        return user;
    }

    @Override
    public boolean existsAuthUserByLogin(String login) {
        return authUserRepository.existsByLogin(login);
    }

    @Override
    public boolean save(AuthUser user) {
        if (user != null) {
            authUserRepository.save(user);

            return true;
        }
        return false;
    }

    @Override
    public boolean update(AuthUser user) {
        if (user != null) {
            authUserRepository.save(user);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(AuthUser user) {
        if (user != null) {
            authUserRepository.delete(user);

            return true;
        }
        return false;
    }

    // --- Trainer ---
    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer getTrainerById(Long id) {
        Trainer trainer = null;

        if (id != null && id > 0L) {
            trainer = trainerRepository.findById(id).get();
        }

        return trainer;
    }

    @Override
    public Trainer getTrainerByLogin(String login) {
        Trainer trainer = null;

        if (login != null && login != "") {
            trainer = trainerRepository.findByLogin(login);
        }

        return trainer;
    }

    @Override
    public boolean existsTrainerByLogin(String login) {
        return trainerRepository.existsByLogin(login);
    }

    @Override
    public boolean save(Trainer trainer) {
        if (trainer != null) {
            trainerRepository.save(trainer);

            authUserRepository.save(new AuthUser(trainer.getLogin(), trainer.getPassword(), UserRole.TRAINER, trainer.getId()));

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Trainer trainer) {
        if (trainer != null) {
            trainerRepository.save(trainer);

            AuthUser user = getAuthUserById(trainer.getId());

            if (user != null) {
                user.setLogin(trainer.getLogin());
                user.setPassword(trainer.getPassword());

                authUserRepository.save(user);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Trainer trainer) {
        if (trainer != null) {
            AuthUser user = getAuthUserById(trainer.getId());

            if (user != null) {
                authUserRepository.delete(user);
            }

            trainerRepository.delete(trainer);

            return true;
        }
        return false;
    }

    // --- Client ---
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        Client client = null;

        if (id != null && id > 0L) {
            client = clientRepository.findById(id).get();
        }

        return client;
    }

    @Override
    public Client getClientByLogin(String login) {
        Client client = null;

        if (login != null && login != "") {
            client = clientRepository.findByLogin(login);
        }

        return client;
    }

    @Override
    public boolean existsClientByLogin(String login) {
        return clientRepository.existsByLogin(login);
    }

    @Override
    public boolean save(Client client) {
        if (client != null) {
            clientRepository.save(client);

            authUserRepository.save(new AuthUser(client.getLogin(), client.getPassword(), UserRole.CLIENT, client.getId()));

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Client client) {
        if (client != null) {
            clientRepository.save(client);

            AuthUser user = getAuthUserById(client.getId());

            if (user != null) {
                user.setLogin(client.getLogin());
                user.setPassword(client.getPassword());

                authUserRepository.save(user);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Client client) {
        if (client != null) {
            AuthUser user = getAuthUserById(client.getId());

            if (user != null) {
                authUserRepository.delete(user);
            }

            clientRepository.delete(client);

            return true;
        }
        return false;
    }

    // --- Admin ---
    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        Admin admin = null;

        if (id != null && id > 0L) {
            admin = adminRepository.findById(id).get();
        }

        return admin;
    }

    @Override
    public Admin getAdminByLogin(String login) {
        Admin admin = null;

        if (login != null && login != "") {
            admin = adminRepository.findByLogin(login);
        }

        return admin;
    }

    @Override
    public boolean existsAdminByLogin(String login) {
        return adminRepository.existsByLogin(login);
    }

    @Override
    public boolean save(Admin admin) {
        if (admin != null) {
            adminRepository.save(admin);

            authUserRepository.save(new AuthUser(admin.getLogin(), admin.getPassword(), UserRole.ADMIN, admin.getId()));

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Admin admin) {
        if (admin != null) {
            adminRepository.save(admin);

            AuthUser user = getAuthUserById(admin.getId());

            if (user != null) {
                user.setLogin(admin.getLogin());
                user.setPassword(admin.getPassword());

                authUserRepository.save(user);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Admin admin) {
        if (admin != null) {

            AuthUser user = getAuthUserById(admin.getId());

            if (user != null) {
                authUserRepository.delete(user);
            }

            adminRepository.delete(admin);

            return true;
        }
        return false;
    }

    // --- TrainingSchedule ---
    @Override
    @Transactional
    public List<TrainingSchedule> getAllTrainingSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public TrainingSchedule getTrainingScheduleById(Long id) {
        TrainingSchedule schedule = null;

        if (id != null && id > 0L) {
            schedule = scheduleRepository.findById(id).get();
        }

        return schedule;
    }

    @Override
    @Transactional
    public boolean save(TrainingSchedule schedule) {
        if (schedule != null) {
            scheduleRepository.save(schedule);

            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(TrainingSchedule schedule) {
        if (schedule != null) {
            TrainingSchedule scheduleDB = getTrainingScheduleById(schedule.getId());

            if (scheduleDB != null) {
                Set<Client> scheduleClients = scheduleDB.getClients();
                Set<Trainer> scheduleTrainers = scheduleDB.getTrainers();
                // remove old clients and trainers
                for (Client c : scheduleClients) {
                    c.getSchedules().removeIf(sc -> sc.getId().equals(schedule.getId()));
                }
                for (Trainer t : scheduleTrainers) {
                    t.getSchedules().removeIf(sc -> sc.getId().equals(schedule.getId()));
                }

                // schedule have only id's clients and trainers
                // set schedule for clients and trainers
                for (Client c : schedule.getClients()) {
                    c = getClientById(c.getId());
                    c.getSchedules().add(schedule);
                    update(c);
                }
                for (Trainer t : schedule.getTrainers()) {
                    t = getTrainerById(t.getId());
                    t.getSchedules().add(schedule);
                    update(t);
                }

                scheduleRepository.save(schedule);

                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(TrainingSchedule schedule) {
        if (schedule != null) {
            scheduleRepository.delete(schedule);

            return true;
        }
        return false;
    }

    // --- Subscription ---
    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getSubscriptionById(Long id) {
        Subscription subscription = null;

        if (id != null && id > 0L) {
            subscription = subscriptionRepository.findById(id).get();
        }

        return subscription;
    }

    @Override
    public boolean save(Subscription subscription) {
        if (subscription != null) {
            subscriptionRepository.save(subscription);

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Subscription subscription) {
        if (subscription != null) {
            subscriptionRepository.save(subscription);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Subscription subscription) {
        if (subscription != null) {
            subscriptionRepository.delete(subscription);

            return true;
        }
        return false;
    }

    // --- Payment history ---
    @Override
    public List<PaymentHistory> getAllPaymentHistories() {
        return paymentHistoryRepository.findAll();
    }

    @Override
    public PaymentHistory getPaymentHistoryById(Long id) {
        PaymentHistory subscription = null;

        if (id != null && id > 0L) {
            subscription = paymentHistoryRepository.findById(id).get();
        }

        return subscription;
    }

    @Override
    public boolean save(PaymentHistory paymentHistory) {
        if (paymentHistory != null) {
            paymentHistoryRepository.save(paymentHistory);

            return true;
        }
        return false;
    }

    @Override
    public boolean update(PaymentHistory paymentHistory) {
        if (paymentHistory != null) {
            paymentHistoryRepository.save(paymentHistory);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(PaymentHistory paymentHistory) {
        if (paymentHistory != null) {
            paymentHistoryRepository.delete(paymentHistory);

            return true;
        }
        return false;
    }
}
