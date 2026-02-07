package com.campusevents.backend.controller;

import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{eventId}")
    public EventResponseDTO enroll(
            @PathVariable Long eventId,
            Authentication authentication
    ) {
        return enrollmentService.enrollInEvent(
                eventId,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @GetMapping("/me")
    public List<EventResponseDTO> myEnrollments(
            Authentication authentication
    ) {
        return enrollmentService.getMyEnrollments(
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }

    @GetMapping("/{eventId}/students")
    public List<String> studentsForEvent(
            @PathVariable Long eventId,
            Authentication authentication
    ) {
        return enrollmentService.getStudentsForEvent(
                eventId,
                authentication.getName(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );
    }
}
