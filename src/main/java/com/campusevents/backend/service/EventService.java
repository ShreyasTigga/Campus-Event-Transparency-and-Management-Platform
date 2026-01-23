package com.campusevents.backend.service;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    EventResponseDTO rejectEvent(Long eventId, String role);
}
