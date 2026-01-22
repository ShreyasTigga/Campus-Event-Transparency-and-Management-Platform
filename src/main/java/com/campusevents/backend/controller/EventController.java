package com.campusevents.backend.controller;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.model.Event;
import com.campusevents.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ==========================
    // CREATE EVENT (Any logged-in user)
    // ==========================
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT','FACULTY','ADMIN')")
    public ResponseEntity<EventResponseDTO> createEvent(
            @RequestBody CreateEventRequestDTO request,
            Authentication authentication
    ) {

        String email = authentication.getName();
        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .organizerName(request.getOrganizerName())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isPublic(request.getIsPublic())
                .build();

        Event saved = eventService.createEvent(event, email, role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toDTO(saved));
    }

    // ==========================
    // VIEW EVENTS (All users)
    // ==========================
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','FACULTY','ADMIN')")
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(
            Authentication authentication
    ) {

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        List<EventResponseDTO> events = eventService.getAllEvents(role)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(events);
    }

    // ==========================
    // GET EVENT BY ID
    // ==========================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT','FACULTY','ADMIN')")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {

        return eventService.getEventById(id)
                .map(event -> ResponseEntity.ok(toDTO(event)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================
    // APPROVE EVENT (ADMIN ONLY)
    // ==========================
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponseDTO> approveEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        Event approved = eventService.approveEvent(id, role);
        return ResponseEntity.ok(toDTO(approved));
    }

    // ==========================
    // REJECT EVENT (ADMIN ONLY)
    // ==========================
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponseDTO> rejectEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        Event rejected = eventService.rejectEvent(id, role);
        return ResponseEntity.ok(toDTO(rejected));
    }

    // ==========================
    // MAPPER
    // ==========================
    private EventResponseDTO toDTO(Event event) {
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
