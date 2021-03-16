package com.zeus.inc.afinams.providers;

import com.zeus.inc.afinams.configs.CustomPasswordEncoder;
import com.zeus.inc.afinams.model.AuthUser;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthUser user = userService.getAuthUserByLogin(login);

        // --- initialize user granted authorities ---
        if (user != null && password != null && new CustomPasswordEncoder().matches(password, user.getPassword())) {
            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));

            log.info("Authenticate - login: " + login + " " + user.getRole().name() + " authentication success");

            return new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);
        } else {
            log.warn("Authenticate - login: " + login + " doesn't exists");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
