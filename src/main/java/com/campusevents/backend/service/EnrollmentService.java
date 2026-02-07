package com.campusevents.backend.service;

import com.campusevents.backend.dto.EventResponseDTO;

import java.util.List;

public interface EnrollmentService {

    EventResponseDTO enrollInEvent(
            Long eventId,
            String studentEmail,
            String role
    );

    List<EventResponseDTO> getMyEnrollments(
            String studentEmail,
            String role
    );

    List<String> getStudentsForEvent(
            Long eventId,
            String requesterEmail,
            String role
    );
}
