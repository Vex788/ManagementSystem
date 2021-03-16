package com.zeus.inc.afinams;

import com.zeus.inc.afinams.model.*;
import com.zeus.inc.afinams.services.UserServiceImpl;
import com.zeus.inc.afinams.utilities.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
@EntityScan("com.zeus.inc.afinams.model")
@EnableJpaRepositories("com.zeus.inc.afinams.repository")
public class AfinamsApplication {

    @Autowired
    private UserServiceImpl userService;

    public static void main(String[] args) {
        SpringApplication.run(AfinamsApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return strings -> {
            TrainingSchedule schedule = new TrainingSchedule(null, new Date(1577809081000L), null, null);

            if (userService.save(schedule)) System.out.println("Training schedule added.");

            Client client = new Client(null, "firstname1", "lastname1", "01878367637",
                    "login1@mail.com", BCrypt.hashpw("password1", BCrypt.gensalt(12)),
                    926, "test test test", "some number of words", "path:/to/avatar.jpg", null);
            Client client2 = new Client(null, "firstname2", "lastname2", "63463454454",
                    "login2@mail.com", BCrypt.hashpw("password2", BCrypt.gensalt(12)),
                    4565, "test test test", "some number of words", "path:/to/avatar.jpg", null);

            Trainer trainer = new Trainer(null, "firstname4", "lastname4", "78926454102",
                    "login4@mail.com", BCrypt.hashpw("password3", BCrypt.gensalt(12)),
                    4565.86, "test test test", "some number of words", "path:/to/avatar.jpg");

            Admin admin = new Admin(null, "firstname5", "lastname5", "78756454102",
                    "login5@mail.com", BCrypt.hashpw("password5", BCrypt.gensalt(12)),
                    4565.86, "test test test", "some number of words", "path:/to/avatar.jpg", UserRole.GOD);

            Subscription subscription = new Subscription(100.0d, 20, "test", "test test test");


            client.addSchedule(schedule);
            client2.addSchedule(schedule);
            trainer.addSchedule(schedule);

            if (userService.save(subscription)) System.out.println("Subscription added.");

            client.setSubscription(subscription);
            client2.setSubscription(subscription);

            if (userService.save(client)) System.out.println("Client1 added.");
            if (userService.save(client2)) System.out.println("Client2 added.");
            if (userService.save(trainer)) System.out.println("Trainer added.");
            if (userService.save(admin)) System.out.println("Admin added.");
        };
    }
}

