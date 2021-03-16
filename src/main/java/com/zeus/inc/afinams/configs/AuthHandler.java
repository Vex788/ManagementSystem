package com.zeus.inc.afinams.configs;

import com.zeus.inc.afinams.model.Client;
import com.zeus.inc.afinams.providers.CustomAuthenticationProvider;
import com.zeus.inc.afinams.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    private String getRandomPassword() {
        return new Random()
                .ints(10, 33, 122)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        System.out.println("onAuthenticationSuccess method");
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();

        Map<String, Object> attributes = user.getAttributes();
        Client newUser = null;

        if (!userService.existsAuthUserByLogin((String) attributes.get("email"))) {
            newUser = new Client(
                    null, null, null, null,
                    (String) attributes.get("email"), getRandomPassword(),
                    null, null, null, null, null
            );
            userService.save(newUser);
        } else {
            newUser = userService.getClientByLogin((String) attributes.get("email"));
            // set is online in user object and update
            userService.update(newUser);
        }

        // create session for redirect to cabinet
        // Create users data cookies
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(newUser.getLogin(), newUser.getPassword());

        // Authenticate the user
        Authentication auth = authenticationProvider.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        // Create a new session and add the security context.
        HttpSession session = httpServletRequest.getSession(true);
        session.setMaxInactiveInterval(10 * 60);
        session.removeAttribute("SPRING_SECURITY_CONTEXT");
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

//        httpServletResponse.sendRedirect("/cabinet");
    }
}
