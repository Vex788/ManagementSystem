package com.zeus.inc.afinams.controllers;

import com.google.gson.JsonObject;
import com.zeus.inc.afinams.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginViaController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    @GetMapping("/login-via")
    public ResponseEntity<Message> LoginVia() {
        System.out.println("login-via controller");

        ClientRegistration clientRegistrationGoogle = clientRegistrationRepository
                .findByRegistrationId("google");
        ClientRegistration clientRegistrationFacebook = clientRegistrationRepository
                .findByRegistrationId("facebook");

        JsonObject json = new JsonObject();

        json.addProperty(clientRegistrationGoogle.getClientName(),
                authorizationRequestBaseUri + "/" + clientRegistrationGoogle.getRegistrationId());
        json.addProperty(clientRegistrationFacebook.getClientName(),
                authorizationRequestBaseUri + "/" + clientRegistrationFacebook.getRegistrationId());
        //json.addProperty("instagramUrl", );

        return new ResponseEntity<>(new Message("/login-via", "failed"), HttpStatus.BAD_REQUEST);
    }


}