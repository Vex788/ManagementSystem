package com.zeus.inc.afinams.configs;

import com.zeus.inc.afinams.model.UserRole;
import com.zeus.inc.afinams.providers.CustomAuthenticationProvider;
import com.zeus.inc.afinams.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(new CustomPasswordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    // Secure the end points with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Enable CSRF protection for POST, PUT, DELETE requests
                .csrf()
                .ignoringAntMatchers("/scheduler/**", "/client/**", "/trainer/**")
                .and()
                // Security headers for XSS protection
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.jsdelivr.net; style-src 'self' 'unsafe-inline'; font-src 'self' fonts.googleapis.com fonts.gstatic.com")
                .and()
                .and()
                // Authorization configuration
                .authorizeRequests()
                .antMatchers("/login*", "/register*", "/css/**", "/js/**", "/vendor/**", "/img/**").permitAll()
                .antMatchers(
                        "/admin/index.html", "/admin/administrators.html", "/admin/clients.html",
                        "/admin/history_of_payments.html", "/admin/trainers.html",
                        "/admin/client_booking.html", "/admin/client_my_sessions.html",
                        "/admin/trainer_schedule.html", "/admin/trainer_analytics.html", "/admin/trainer_clients.html"
                ).hasAnyAuthority(UserRole.ADMIN.name(), UserRole.GOD.name(), "CLIENT", "TRAINER")
                .antMatchers("/admin/god.html").hasAuthority(UserRole.GOD.name())
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                // HTTP Basic authentication
                .httpBasic()
                .and().authenticationProvider(authenticationProvider())
                // OAuth2 Login configuration
                .and()
                .oauth2Login()
                .loginPage("/login.html")
                .successHandler(authenticationSuccessHandler)
                .and()
                // Logout configuration
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .and()
                // Session management
                .sessionManagement()
                .maximumSessions(2)
                .expiredUrl("/too-many-sessions")
                .and()
                .sessionFixationProtection()
                .migrateSession();
    }
}
