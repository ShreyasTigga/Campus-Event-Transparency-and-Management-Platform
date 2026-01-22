package com.campusevents.backend.service;

import com.campusevents.backend.exception.AccessDeniedException;
import com.campusevents.backend.exception.ResourceNotFoundException;
import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    // ======================
    // CREATE EVENT
    // ======================
    @Override
    public Event createEvent(Event event, String creatorEmail, String role) {

        // Only authenticated users can create
        if (role == null || role.isBlank()) {
            throw new AccessDeniedException("Unauthorized to create event");
        }

        event.setId(null); // ensure new record
        event.setCreatedByEmail(creatorEmail);
        event.setStatus(EventStatus.PENDING); // Always pending approval

        return eventRepository.save(event);
    }

    // ======================
    // GET ALL EVENTS
    // ======================
    @Override
    public List<Event> getAllEvents(String role) {

        // ADMIN can see everything
        if ("ADMIN".equalsIgnoreCase(role)) {
            return eventRepository.findAll();
        }

        // Non-admins see only PUBLIC + APPROVED events
        return eventRepository
                .findByIsPublicTrueAndStatus(EventStatus.APPROVED);
    }

    // ======================
    // GET EVENT BY ID
    // ======================
    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // ======================
    // APPROVE EVENT (ADMIN ONLY)
    // ======================
    @Override
    public Event approveEvent(Long eventId, String role) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only ADMIN can approve events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found with ID: " + eventId)
                );

        event.setStatus(EventStatus.APPROVED);
        return eventRepository.save(event);
    }

    // ======================
    // REJECT EVENT (ADMIN ONLY)
    // ======================
    @Override
    public Event rejectEvent(Long eventId, String role) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only ADMIN can reject events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found with ID: " + eventId)
                );

        event.setStatus(EventStatus.REJECTED);
        return eventRepository.save(event);
    }
}
