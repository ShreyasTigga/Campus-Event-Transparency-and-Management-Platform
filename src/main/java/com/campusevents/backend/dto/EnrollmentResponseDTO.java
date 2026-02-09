package com.campusevents.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponseDTO {
    private Long id;
    private String studentEmail;
}
