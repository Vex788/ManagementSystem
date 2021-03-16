package com.zeus.inc.afinams.model;

public enum UserRole {
    GOD, ADMIN, TRAINER, CLIENT, ANONYMOUS;

    @Override
    public String toString() {
        return name();
    }
}
