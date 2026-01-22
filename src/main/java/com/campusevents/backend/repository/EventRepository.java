package com.campusevents.backend.repository;

import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsPublicTrueAndStatus(EventStatus status);
}
