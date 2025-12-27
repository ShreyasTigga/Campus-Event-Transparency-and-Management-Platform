package com.campusevents.backend.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role; // STUDENT / ORGANIZER / ADMIN
}
