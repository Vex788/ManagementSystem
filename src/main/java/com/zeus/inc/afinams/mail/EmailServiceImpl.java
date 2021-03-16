package com.zeus.inc.afinams.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

    private static final String FROM = "email@gmail.com"; // ToDo: change default email for AFINA studio

    private String confirmEmailTemplate =
            "<center><h2 style=\"display: inline-flex;\">AFINA<div style=\"color:yellow;\">Studio</div></h2><br><h3>" +
                    "To confirm the email click on the <a href=\"%s\">link</a></h3></center>";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void sendMessage(String email, String subject, String messageText) {
        try {
            JavaMailSenderImpl sender = applicationContext.getBean(JavaMailSenderImpl.class);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(subject);
            helper.setFrom(FROM);
            helper.setTo(email);

            helper.setText(messageText, false);

            sender.send(message);
        } catch (MailSendException mse) {
            mse.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void sendVerifyEmailMessage(String email, String urlWithToken) {
        try {
            JavaMailSenderImpl sender = applicationContext.getBean(JavaMailSenderImpl.class);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("Email confirmation");
            helper.setFrom(FROM);
            helper.setTo(email);

            helper.setText(String.format(confirmEmailTemplate, urlWithToken), true);

            sender.send(message);
        } catch (MailSendException mse) {
            mse.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}