package com.campusevents.backend.service;

import com.campusevents.backend.dto.LoginRequestDTO;
import com.campusevents.backend.dto.LoginResponseDTO;
import com.campusevents.backend.dto.UserRequestDTO;
import com.campusevents.backend.dto.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(UserRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
