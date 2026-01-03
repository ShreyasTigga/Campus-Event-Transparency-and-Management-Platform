package com.campusevents.backend.service;

import com.campusevents.backend.dto.UserRequestDTO;
import com.campusevents.backend.dto.UserResponseDTO;
import com.campusevents.backend.model.User;
import com.campusevents.backend.repository.UserRepository;
import com.campusevents.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.campusevents.backend.dto.LoginRequestDTO;
import com.campusevents.backend.dto.LoginResponseDTO;
import com.campusevents.backend.exception.EmailAlreadyExistsException;
import com.campusevents.backend.exception.InvalidCredentialsException;
import com.campusevents.backend.exception.UserNotFoundException;
import com.campusevents.backend.exception.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
