package com.campusevents.backend.service;

import com.campusevents.backend.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event createEvent(Event event);

    List<Event> getAllEvents();

    Optional<Event> getEventById(Long id);
}
