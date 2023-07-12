package com.codeup.autoconnect.config;

import com.codeup.autoconnect.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
// Will allow us to edit the MVC security for our application
@EnableWebSecurity
public class SecurityConfiguration {
    // Dependency that we inject, so that we can retrieve details about the user who is trying to log in.
    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }


    // The @Bean annotation means that the class itself is being managed by Spring.

    // Is a class that is managed by Spring, specifically to hash and unhash our User passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This class is used to manage the users Authentication status.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // This class will provide filters for our Spring security for different URL mappings.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        /* Pages that require authentication
                         * only authenticated users can create and edit and delete posts! */


                        .requestMatchers(
                                "/profile",
                                "/profile/*/edit",
                                "/profile/*/setting",
                                "/profile/*/setting/password",
                                "/profile/*/setting/delete",
                                "/posts/create",
                                "/posts/*/edit",
                                "/create-appointment",
                                "/api/appointments",
                                "/posts/*/delete",
                                "/review/*/create",
                                "/review/*/edit",
                                "/posts/*/post",
                                "/posts/*/comment",
                                "/review/*/delete",
                                "/payment/*",
                                "/paymentDetail/*/edit",
                                "/paymentDetail/*/delete",
                                "/messages",
                                "/messages/search",
                                "/messages/new-conversation",
                                "/messages/send",
                                "/messages/load",
                                "/messages/conversation/*",
                                "/messages/load-conversation/*",
                                "/messages/delete").authenticated()

                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, and view ads */


                        .requestMatchers("/",
                                "/contacts",
                                "/about",
                                "/posts",
                                "/profile/*",
                                "/review/*",
                                "/registration",
                                "/login",
                                "/MechanicSearchPage",
                                "/users/mechanic-list*",
                                "/appointments/*",
                                "/paymentDetail/*").permitAll()

                        // allow loading of static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**","/keys.js", "/favicon.ico").permitAll()
                )
                /* Login configuration */
                .formLogin((login) -> login.loginPage("/login").defaultSuccessUrl("/profile"))
                /* Logout configuration */
                .logout((logout) -> logout.logoutSuccessUrl("/login?logout"))
                .httpBasic(withDefaults());
        return http.build();
    }
}