package com.campusevents.backend.service;

import java.util.List;
import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;

public interface EventService {

    EventResponseDTO createEvent(CreateEventRequestDTO request, String creatorEmail);

    List<EventResponseDTO> getApprovedEvents();

    EventResponseDTO approveEvent(Long eventId);

    EventResponseDTO rejectEvent(Long eventId);
}

