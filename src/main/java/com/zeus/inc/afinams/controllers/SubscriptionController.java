package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Subscription;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Subscription>> onAuthentication(HttpServletRequest request) {
        try {
            List<Subscription> subscriptions = userService.getAllSubscriptions();

            if (subscriptions != null) {
                return new ResponseEntity<>(subscriptions, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody Subscription subscription, HttpServletRequest request) {
        try {
            if (subscription != null && subscription.getId() != null) {
                if (userService.update(subscription)) {
                    return new ResponseEntity<>(new Message("/subscription/update", "Success"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new Message("/subscription/update", "ID not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> onAdd(@RequestBody Subscription subscription, HttpServletRequest request) {
        try {
            if (subscription != null && subscription.getId() == null) {
                if (userService.save(subscription)) {
                    return new ResponseEntity<>(new Message("/subscription/add", "Success"), HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete subscription by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Subscription subscription = userService.getSubscriptionById(id);

            if (subscription != null) {
                if (userService.delete(subscription)) {
                    return new ResponseEntity<>(subscription, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/subscription/delete/" + id, "Subscription by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
