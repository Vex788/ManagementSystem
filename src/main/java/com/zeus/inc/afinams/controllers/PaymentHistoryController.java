package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.model.PaymentHistory;
import com.zeus.inc.afinams.model.Subscription;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/payment-history")
public class PaymentHistoryController {

    @Autowired
    private UserServiceImpl userService;

    private String accrualOfVisits(PaymentHistory paymentHistory) {
        String result = null;

        if (paymentHistory != null && paymentHistory.getSubscriptionPay() != null
                && paymentHistory.getSubscriptionPay().getId() != null) {
            Client client = userService.getClientById(
                    paymentHistory.getToPay() != null ? paymentHistory.getToPay().getId() : null
            );
            Subscription subscription = userService.getSubscriptionById(paymentHistory.getSubscriptionPay().getId());

            if (client != null && subscription != null) {
                // --- if infinity days count and subcription updated date > null ---
                if (client.getNumberOfPaidWorkouts().equals(Integer.MAX_VALUE)
                        && (new Date().after(client.getSubscriptionUpdated()) || client.getSubscriptionUpdated() == null)) {
                    client.setNumberOfPaidWorkouts(subscription.getDaysCount());
                    client.setSubscription(subscription);

                    userService.update(client);

                    result = "Subscription is success";
                } else if (result == null &&
                        (client.getNumberOfPaidWorkouts() == null || client.getNumberOfPaidWorkouts() == 0)) {
                    client.setNumberOfPaidWorkouts(subscription.getDaysCount());
                    client.setSubscription(subscription);

                    userService.update(client);

                    result = "Subscription is success";
                } else {
                    result = "Something wrong";
                }
            }
        }

        return result;
    }

    /**
     * This method update trainer.
     *
     * @param paymentHistory
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Message> onUpdate(@RequestBody PaymentHistory paymentHistory, HttpServletRequest request) {
        try {
            PaymentHistory paymentHistoryDB = userService.getPaymentHistoryById(paymentHistory.getId());

            if (paymentHistoryDB != null) {
                if (userService.update(paymentHistory)) {
                    accrualOfVisits(paymentHistory);

                    return new ResponseEntity<>(new Message("/payment-history/update", "Success"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new Message("/payment-history/update", "ID not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> onAdd(@RequestBody PaymentHistory paymentHistory, HttpServletRequest request) {
        try {
            if (paymentHistory != null && paymentHistory.getId() == null) {
                if (userService.save(paymentHistory)) {
                    accrualOfVisits(paymentHistory);

                    return new ResponseEntity<>(new Message("/payment-history/add", "Success"), HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select trainer by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> onGetById(@PathVariable Long id, HttpServletRequest request) {
        try {
            PaymentHistory paymentHistory = userService.getPaymentHistoryById(id);

            if (paymentHistory != null) {
                return new ResponseEntity<>(paymentHistory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new Message("/payment-history/get/" + id, "PaymentHistory by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method select all trainers.
     *
     * @param request
     * @return
     */
    @GetMapping("/get-all")
    public ResponseEntity<java.util.List<PaymentHistory>> onGetAll(HttpServletRequest request) {
        try {
            java.util.List<PaymentHistory> paymentHistories = userService.getAllPaymentHistories();

            if (paymentHistories != null) {
                return new ResponseEntity<>(paymentHistories, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method delete trainer by id.
     *
     * @param request
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> onDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            PaymentHistory paymentHistory = userService.getPaymentHistoryById(id);

            if (paymentHistory != null) {
                if (userService.delete(paymentHistory)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(
                        new Message("/payment-history/delete/" + id, "PaymentHistory by " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
