package com.zeus.inc.afinams.mail;

public interface EmailService {

    void sendMessage(String email, String subject, String messageText);

    void sendVerifyEmailMessage(String email, String urlWithToken);
}
