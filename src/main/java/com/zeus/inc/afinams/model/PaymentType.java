package com.zeus.inc.afinams.model;

public enum PaymentType {
    SALARY, CONSUMPTION, INCOME, SUBSCRIPTION;

    @Override
    public String toString() {
        return name();
    }
}
