package com.campusevents.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        excludeName = {
                "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
        }
)
public class CampusEventsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusEventsBackendApplication.class, args);
    }
}
