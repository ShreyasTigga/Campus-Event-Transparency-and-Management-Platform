package com.campusevents.backend.service;

import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.repository.EventRepository;
import com.campusevents.backend.exception.AccessDeniedException;
import com.campusevents.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event createEvent(Event event, String creatorEmail, String role) {

        event.setCreatedByEmail(creatorEmail);

        // STUDENT events must be approved
        if ("STUDENT".equals(role)) {
            event.setStatus(EventStatus.PENDING);
        } else {
            // FACULTY / ADMIN auto-approved
            event.setStatus(EventStatus.APPROVED);
        }

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents(String role) {

        if ("ADMIN".equals(role) || "FACULTY".equals(role)) {
            return eventRepository.findAll();
        }

        // STUDENT → only approved events
        return eventRepository.findByStatus(EventStatus.APPROVED);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event approveEvent(Long eventId, String role) {

        if (!"ADMIN".equals(role) && !"FACULTY".equals(role)) {
            throw new AccessDeniedException("You are not allowed to approve events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setStatus(EventStatus.APPROVED);
        return eventRepository.save(event);
    }

    @Override
    public Event rejectEvent(Long eventId, String role) {

        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admin can reject events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setStatus(EventStatus.REJECTED);
        return eventRepository.save(event);
    }
}
