package com.campusevents.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private String role; // STUDENT / ADMIN
}
