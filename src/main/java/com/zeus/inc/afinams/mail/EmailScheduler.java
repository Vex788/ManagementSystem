package com.zeus.inc.afinams.mail;

import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServiceImpl userService;
/**
 * This method select all users and send email every 60 minutes.
 */
//    @Scheduled(fixedRate = 60000)
//    public void sendNotifications() {
//        System.out.println("Starting scheduler");
//        List<CUser> userList = userService.getAllUsers();
//
//        if (userList != null && !userList.isEmpty()) {
//            for (CUser user : userList) {
//
//            }
//        }
//        System.out.println("Finish scheduler");
//    }
}
