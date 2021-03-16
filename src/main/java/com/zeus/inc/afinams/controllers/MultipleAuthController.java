package com.zeus.inc.afinams.controllers;

import com.google.gson.JsonObject;
import com.zeus.inc.afinams.model.AuthUser;
import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class MultipleAuthController {

    @Autowired
    private UserService userService;

    private final String ACCOUNT_SID = "AC23325ee0ab135a4442a4157aa524c6e5";
    private final String AUTH_TOKEN = "6dc00ae7581ca3ac39d33132933e4467";
    private final String TWILIO_NUMBER = "+12562570106";
    // ToDo: generate code add phone number like salt and create a new user if doesn't exists,
    // save him and send to user

    @GetMapping("/login/generate-code/{phone}")
    public ResponseEntity<Object> getGeneratedCode(@PathVariable String phone, HttpServletRequest request) {
        System.out.println("multipleauthcontroller");
        JsonObject json = new JsonObject();
        if (phone != null && !phone.isEmpty()) {
            System.out.println("phone number: " + phone);
            int code = 82362;    // generated code

            // --- get code from twillio ---
//			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//		
//			Message message = Message.creator(
//			    new com.twilio.type.PhoneNumber("+380950069350"),
//			    new com.twilio.type.PhoneNumber(TWILIO_NUMBER),
//			    "Test of a multiple authentication. Code " + code)
//			.create();

            String passwordHash = BCrypt.hashpw("" + code, BCrypt.gensalt(12));
            AuthUser authUser = userService.getAuthUserByLogin(phone);

            if (authUser != null) {
                authUser.setPassword(passwordHash);

                userService.update(authUser);
            } else {
                Client client = new Client(null, null, null, phone, phone, passwordHash, 0, null, null, null, null);

                userService.save(client);
            }

            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(2592000); // 30 days it's a 2592000 seconds

            json.addProperty("phone", phone);
            json.addProperty("code", code);
        }

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


}