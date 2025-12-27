package com.campusevents.backend.config;

import com.campusevents.backend.model.User;
import com.campusevents.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@campus.com").isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@campus.com")
                    .password("admin123")
                    .role("ADMIN")
                    .build();

            userRepository.save(admin);
        }
    }
}
