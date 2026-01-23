package com.campusevents.backend.repository;

import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Public approved events (for STUDENTS / FACULTY)
    Page<Event> findByStatusAndIsPublicTrue(EventStatus status, Pageable pageable);

    // Admin can see everything
    Page<Event> findAll(Pageable pageable);

    // User's own events
    Page<Event> findByCreatedByEmail(String email, Pageable pageable);
}
