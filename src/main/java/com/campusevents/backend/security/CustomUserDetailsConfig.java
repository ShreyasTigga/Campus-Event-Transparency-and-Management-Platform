package com.campusevents.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class CustomUserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Disable default in-memory authentication completely
        return username -> {
            throw new UnsupportedOperationException(
                    "UserDetailsService is disabled. JWT authentication will be used."
            );
        };
    }
}
