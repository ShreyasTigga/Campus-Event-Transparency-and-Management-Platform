package com.campusevents.backend.controller;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public EventResponseDTO createEvent(
            @RequestBody CreateEventRequestDTO request,
            Authentication authentication
    ) {
        return eventService.createEvent(
                request,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @GetMapping
    public Page<EventResponseDTO> getEvents(
            Pageable pageable,
            Authentication authentication
    ) {
        return eventService.getEvents(
                authentication.getAuthorities().iterator().next().getAuthority(),
                authentication.getName(),
                pageable
        );
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEventById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return eventService.getEventById(
                id,
                authentication.getAuthorities().iterator().next().getAuthority(),
                authentication.getName()
        );
    }

    @PostMapping("/{id}/approve")
    public EventResponseDTO approveEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return eventService.approveEvent(
                id,
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @PostMapping("/{id}/reject")
    public EventResponseDTO rejectEvent(
            @PathVariable Long id,
            Authentication authentication,
            @RequestParam String comment
    ) {
        return eventService.rejectEvent(
                id,
                authentication.getAuthorities().iterator().next().getAuthority(),
                comment
        );
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(
            @PathVariable Long id,
            @RequestBody CreateEventRequestDTO request,
            Authentication authentication
    ) {
        return eventService.updateEvent(
                id,
                request,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {
        eventService.deleteEvent(
                id,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @PostMapping("/{id}/enroll")
    public void enroll(
            @PathVariable Long id,
            Authentication authentication
    ) {
        eventService.enrollInEvent(
                id,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

}
