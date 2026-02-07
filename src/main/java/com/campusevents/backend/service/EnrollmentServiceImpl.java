package com.campusevents.backend.service;

import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.exception.AccessDeniedException;
import com.campusevents.backend.exception.ResourceNotFoundException;
import com.campusevents.backend.model.Enrollment;
import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.repository.EnrollmentRepository;
import com.campusevents.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EventRepository eventRepository;

    @Override
    public EventResponseDTO enrollInEvent(
            Long eventId,
            String studentEmail,
            String role
    ) {

        if (!role.equals("STUDENT")) {
            throw new AccessDeniedException("Only students can enroll");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        if (event.getStatus() != EventStatus.APPROVED || !event.getIsPublic()) {
            throw new AccessDeniedException("Event not open for enrollment");
        }

        if (enrollmentRepository.existsByStudentEmailAndEvent(studentEmail, event)) {
            throw new AccessDeniedException("Already enrolled in this event");
        }

        Enrollment enrollment = Enrollment.builder()
                .studentEmail(studentEmail)
                .event(event)
                .build();

        enrollmentRepository.save(enrollment);

        return mapToDTO(event);
    }

    @Override
    public List<EventResponseDTO> getMyEnrollments(
            String studentEmail,
            String role
    ) {

        if (!role.equals("STUDENT")) {
            throw new AccessDeniedException("Only students can view enrollments");
        }

        return enrollmentRepository.findByStudentEmail(studentEmail)
                .stream()
                .map(Enrollment::getEvent)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStudentsForEvent(
            Long eventId,
            String requesterEmail,
            String role
    ) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        if (!role.equals("ADMIN") &&
                !event.getCreatedByEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("Not authorized to view enrollments");
        }

        return enrollmentRepository.findByEvent(event)
                .stream()
                .map(Enrollment::getStudentEmail)
                .collect(Collectors.toList());
    }

    // Mapper
    private EventResponseDTO mapToDTO(Event event) {
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
