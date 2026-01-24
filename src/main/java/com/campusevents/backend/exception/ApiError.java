package com.campusevents.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private String path;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private int status;
}

