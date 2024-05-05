package com.moe.demo.security;

import com.moe.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configure ->
                        configure
                                .requestMatchers("/products").hasRole("EMPLOYEE")
                                .requestMatchers("/products/create").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/products/store").hasRole("MANAGER")
                                .requestMatchers("/products/*/edit").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/products/*/update").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/products/*/delete").hasRole("ADMIN")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                                .logoutSuccessUrl("/login")
                )
                .exceptionHandling(configure ->
                        configure.accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}
