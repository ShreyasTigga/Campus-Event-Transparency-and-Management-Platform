package com.campusevents.backend.controller;

import com.campusevents.backend.service.EventService;
import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.dto.CreateEventRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<EventResponseDTO> createEvent(
            @Valid @RequestBody CreateEventRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(request, authentication.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public List<EventResponseDTO> getApprovedEvents() {
        return eventService.getApprovedEvents();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponseDTO approve(@PathVariable Long id) {
        return eventService.approveEvent(id);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponseDTO reject(@PathVariable Long id) {
        return eventService.rejectEvent(id);
    }
}
