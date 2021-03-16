package com.zeus.inc.afinams.model;


import com.zeus.inc.afinams.services.UserServiceImpl;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Long userId;


    @OneToMany(mappedBy = "whoPay")
    private Set<PaymentHistory> paymentHistoryWhoPay = new HashSet<>();

    @OneToMany(mappedBy = "toPay")
    private Set<PaymentHistory> paymentHistoryToPay = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


    public void setTime() {
        this.created = created == null ? new Date(System.currentTimeMillis()) : created;
        this.updated = new Date(System.currentTimeMillis());
    }

    public AuthUser() {
        setTime();
    }

    public AuthUser(String login, String password, UserRole role, Long userId) {
        setTime();
        this.login = login;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<PaymentHistory> getPaymentHistoryWhoPay() {
        return paymentHistoryWhoPay;
    }

    public void setPaymentHistoryWhoPay(Set<PaymentHistory> paymentHistoryWhoPay) {
        this.paymentHistoryWhoPay = paymentHistoryWhoPay;
    }

    public Set<PaymentHistory> getPaymentHistoryToPay() {
        return paymentHistoryToPay;
    }

    public void setPaymentHistoryToPay(Set<PaymentHistory> paymentHistoryToPay) {
        this.paymentHistoryToPay = paymentHistoryToPay;
    }

    /**
     * This method return object by role and id.
     * Object can be cast to a implemented by role.
     *
     * @param userService
     * @return
     */
    public Object getPerson(UserServiceImpl userService) {
        switch (role) {
            case ADMIN: {
                return userService.getAdminById(userId);
            }
            case GOD: {
                return userService.getAdminById(userId);
            }
            case TRAINER: {
                return userService.getTrainerById(userId);
            }
            case CLIENT: {
                return userService.getClientById(userId);
            }
            default: {
                return null;
            }
        }
    }
}
