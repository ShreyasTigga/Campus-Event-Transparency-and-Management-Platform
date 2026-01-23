package com.campusevents.backend.service;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.exception.AccessDeniedException;
import com.campusevents.backend.exception.ResourceNotFoundException;
import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponseDTO createEvent(
            CreateEventRequestDTO request,
            String creatorEmail,
            String role
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

        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public Page<EventResponseDTO> getEvents(
            String role,
            String email,
            Pageable pageable
    ) {
        Page<Event> events;

        if (role.equals("ADMIN")) {
            events = eventRepository.findAll(pageable);
        } else {
            events = eventRepository.findByStatusAndIsPublicTrue(
                    EventStatus.APPROVED,
                    pageable
            );
        }

        return events.map(this::mapToDTO);
    }

    @Override
    public EventResponseDTO getEventById(
            Long id,
            String role,
            String email
    ) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        if (!role.equals("ADMIN")) {
            if (!event.getIsPublic() ||
                    event.getStatus() != EventStatus.APPROVED) {
                throw new AccessDeniedException("You cannot view this event");
            }
        }

        return mapToDTO(event);
    }

    @Override
    public EventResponseDTO approveEvent(Long eventId, String role) {
        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Only admins can approve events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        event.setStatus(EventStatus.APPROVED);
        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO rejectEvent(Long eventId, String role) {
        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Only admins can reject events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        event.setStatus(EventStatus.REJECTED);
        return mapToDTO(eventRepository.save(event));
    }

    // 🔁 Mapper
    private EventResponseDTO mapToDTO(Event event) {
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
