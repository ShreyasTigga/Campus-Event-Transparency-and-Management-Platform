package com.campusevents.backend.controller;

import com.campusevents.backend.model.Event;
import com.campusevents.backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ================= CREATE EVENT =================
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestBody Event event,
            Authentication authentication
    ) {
        String email = authentication.getName();
        String role = extractRole(authentication);

        Event savedEvent = eventService.createEvent(event, email, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    // ================= GET ALL EVENTS =================
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(Authentication authentication) {
        String role = extractRole(authentication);
        return ResponseEntity.ok(eventService.getAllEvents(role));
    }

    // ================= GET EVENT BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= APPROVE EVENT =================
    @PutMapping("/{id}/approve")
    public ResponseEntity<Event> approveEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String role = extractRole(authentication);
        return ResponseEntity.ok(eventService.approveEvent(id, role));
    }

    // ================= REJECT EVENT =================
    @PutMapping("/{id}/reject")
    public ResponseEntity<Event> rejectEvent(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String role = extractRole(authentication);
        return ResponseEntity.ok(eventService.rejectEvent(id, role));
    }

    // ================= HELPER =================
    private String extractRole(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
