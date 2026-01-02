package com.campusevents.backend.service;

import com.campusevents.backend.model.Event;
import com.campusevents.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponseDTO createEvent(
            CreateEventRequestDTO request,
            String creatorEmail
    ) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .organizerName(request.getOrganizerName())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isPublic(request.getIsPublic())
                .createdByEmail(creatorEmail)
                .status(EventStatus.PENDING)
                .build();

        return mapToResponse(eventRepository.save(event));
    }

    @Override
    public List<EventResponseDTO> getApprovedEvents() {
        return eventRepository.findByStatus(EventStatus.APPROVED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EventResponseDTO approveEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setStatus(EventStatus.APPROVED);
        return mapToResponse(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO rejectEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setStatus(EventStatus.REJECTED);
        return mapToResponse(eventRepository.save(event));
    }

    private EventResponseDTO mapToResponse(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(event.getCategory())
                .organizerName(event.getOrganizerName())
                .location(event.getLocation())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .isPublic(event.getIsPublic())
                .status(event.getStatus())
                .createdByEmail(event.getCreatedByEmail())
                .build();
    }
}
