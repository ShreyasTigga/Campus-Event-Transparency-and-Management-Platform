package com.campusevents.backend.service;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.campusevents.backend.dto.EnrollmentResponseDTO;

public interface EventService {

    EventResponseDTO createEvent(
            CreateEventRequestDTO request,
            String creatorEmail,
            String role
    );

    Page<EventResponseDTO> getEvents(
            String role,
            String email,
            Pageable pageable
    );

    EventResponseDTO getEventById(
            Long id,
            String role,
            String email
    );

    EventResponseDTO approveEvent(Long eventId, String role);

    EventResponseDTO rejectEvent(Long eventId, String role, String comment);

    EventResponseDTO updateEvent(
            Long eventId,
            CreateEventRequestDTO request,
            String email,
            String role
    );

    void deleteEvent(Long eventId, String email, String role);

    public void enrollInEvent(Long eventId, String email, String role);

    Page<EnrollmentResponseDTO> getEnrollments(
            Long eventId,
            String email,
            String role,
            Pageable pageable
    );
}
