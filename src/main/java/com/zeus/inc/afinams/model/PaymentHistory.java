package com.zeus.inc.afinams.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    private String subscription;

    @ManyToOne
    @JoinColumn(name = "sub_id")
    private Subscription subscriptionPay;

    @ManyToOne
    @JoinColumn(name = "auwp_id")
    private AuthUser whoPay;

    @ManyToOne
    @JoinColumn(name = "autp_id")
    private AuthUser toPay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


    public void setTime() {
        this.created = created == null ? new Date(System.currentTimeMillis()) : created;
        this.updated = new Date(System.currentTimeMillis());
    }

    public PaymentHistory() {
        setTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscriptionPay() {
        return subscriptionPay;
    }

    public void setSubscriptionPay(Subscription subscriptionPay) {
        this.subscriptionPay = subscriptionPay;
    }

    public AuthUser getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(AuthUser whoPay) {
        this.whoPay = whoPay;
    }

    public AuthUser getToPay() {
        return toPay;
    }

    public void setToPay(AuthUser toPay) {
        this.toPay = toPay;
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
}
