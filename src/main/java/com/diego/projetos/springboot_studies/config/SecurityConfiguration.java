package com.diego.projetos.springboot_studies.config;

//import com.diego.projetos.springboot_studies.service.ProjectUserDetailsService;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
// @EnableWebSecurity -> optional because Spring Boot already enables it
@EnableMethodSecurity
public class SecurityConfiguration {
    /***
     Spring Authentication Filters:
     - BasicAuthenticationFilter
     - UsernamePasswordAuthenticationFilter
     - DefaultLoginPageGeneratingFilter
     - DefaultLogoutPageGeneratingFilter
     - FilterSecurityInterceptor

     Authentication -> Authorization
     ***/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // note 1
                .csrf(AbstractHttpConfigurer::disable) // note 2
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("animes/admin/**").hasRole("ADMIN")
                        .requestMatchers("animes/**").hasRole("USER") // note 4
                        .anyRequest()
                        .authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()); // note 3

        return http.build();
    }
    // states that any HTTP request must be authenticated
    // basically it defines the security laws of the application

    // note 1:
    // we could generate a CSFR token as showed below, but not going to because it makes testing difficult,
    // although in real applications, it's the preferred way
    //      .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
    // *obs: CookieCsrfTokenRepository doesn't create a token, instead it defines:
    //       "if there is a token, it will be stored in a cookie"

    // note 2:
    // same as csrf.disable(), but with method reference

    // note 3:
    // Customizer.withDefaults enables HTTP Basic authentication with default Spring Security configuration

    // note 4:
    // IT HAS TO BE IN THIS ORDER (more restrictive first)
    // the reason is that if thing1/** is first, thing1/thing2/*** will fit in the first category
    // because thing/*** is **, so you have to be careful with that

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // note: PasswordEncoder -> interface
        // BCryptPasswordEncoder -> implementation
    }
    // everytime the encoder is called, it generates a random hash associated with a given password
    // the hash is the one saved into the database for security
    // after that, to compare that a given login password is in the database, it compares
    // the provided password and the value in the database
    // if they match then the login is authorized

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        log.info("Password encoded: {}", passwordEncoder.encode("diego123"));
//        log.info(">>> UserDetailsService criado");
//
//        UserDetails admin = User.withUsername("diego")
//                .password(passwordEncoder.encode("diego123")) // wraps the password inside the encoder
//                .roles("USER", "ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("user123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
    // creates new in memory users (created every time that the application starts)
    // not needed because now users are being created directly into the database
}
