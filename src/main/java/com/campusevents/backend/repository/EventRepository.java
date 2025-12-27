package com.campusevents.backend.repository;

import com.campusevents.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // later you can add custom queries, e.g.
    // List<Event> findByTitleContainingIgnoreCase(String title);
}
