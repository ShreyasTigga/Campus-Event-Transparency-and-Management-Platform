package com.campusevents.backend.service;

import com.campusevents.backend.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event createEvent(Event event, String creatorEmail, String role);

    List<Event> getAllEvents(String role);

    Optional<Event> getEventById(Long id);

    Event approveEvent(Long eventId, String role);

    Event rejectEvent(Long eventId, String role);
}
