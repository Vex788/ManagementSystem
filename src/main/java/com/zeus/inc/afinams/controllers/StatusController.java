package com.zeus.inc.afinams.controllers;

import com.zeus.inc.afinams.dto.PersonDTO;
import com.zeus.inc.afinams.message.Message;
import com.zeus.inc.afinams.model.Admin;
import com.zeus.inc.afinams.model.AuthUser;
import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.model.Trainer;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * This method get user from session.
     *
     * @return
     */
    private ResponseEntity<Message> isAuthentication(String login) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authLogin = auth.getName();

        if (authLogin.equals(login) && auth.isAuthenticated()) {
            return new ResponseEntity<>(new Message("/status/session", "Success"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("/status/session", "Failed"), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method check user is authenticate.
     *
     * @param login
     * @param request
     * @return
     */
    @GetMapping("/authentication/{login}")
    public ResponseEntity<Message> onAuthentication(@PathVariable(name = "login") String login, HttpServletRequest request) {
        try {
            return isAuthentication(login);
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user-info")
    public ResponseEntity<PersonDTO> onUserInfo(HttpServletRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String authLogin = auth.getName();
            AuthUser authUser = userService.getAuthUserByLogin(authLogin);
            PersonDTO personDTO = null;

            if (authUser != null) {
                Object userObject = authUser.getPerson(userService);

                if (userObject instanceof Admin) {
                    personDTO = ((Admin) userObject).toDTO();
                } else if (userObject instanceof Trainer) {
                    personDTO = ((Trainer) userObject).toDTO();
                } else if (userObject instanceof Client) {
                    personDTO = ((Client) userObject).toDTO();
                }

                return new ResponseEntity<>(personDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
