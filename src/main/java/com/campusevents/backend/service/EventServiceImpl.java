package com.campusevents.backend.service;

import com.campusevents.backend.dto.CreateEventRequestDTO;
import com.campusevents.backend.dto.EventResponseDTO;
import com.campusevents.backend.exception.AccessDeniedException;
import com.campusevents.backend.exception.ResourceNotFoundException;
import com.campusevents.backend.model.Event;
import com.campusevents.backend.model.EventStatus;
import com.campusevents.backend.model.Enrollment;
import com.campusevents.backend.repository.EventRepository;
import com.campusevents.backend.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EventResponseDTO createEvent(
            CreateEventRequestDTO request,
            String creatorEmail,
            String role
    ) {
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .organizerName(request.getOrganizerName())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isPublic(request.getIsPublic())
                .createdByEmail(creatorEmail)
                .status(EventStatus.PENDING)
                .build();

        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public Page<EventResponseDTO> getEvents(
            String role,
            String email,
            Pageable pageable
    ) {

        Page<Event> events;

        if (role.equals("ADMIN")) {
            events = eventRepository.findAll(pageable);

        } else if (role.equals("ORGANIZER")) {
            events = eventRepository.findByCreatedByEmail(email, pageable);

        } else {
            events = eventRepository.findByStatusAndIsPublicTrue(
                    EventStatus.APPROVED,
                    pageable
            );
        }

        return events.map(this::mapToDTO);
    }



    @Override
    public EventResponseDTO getEventById(
            Long id,
            String role,
            String email
    ) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (role.equals("ADMIN")) {
            return mapToDTO(event);
        }

        if (role.equals("ORGANIZER")) {

            if (!event.getCreatedByEmail().equals(email)) {
                throw new AccessDeniedException("Not your event");
            }

            return mapToDTO(event);
        }

        // Student
        if (!event.getIsPublic() || event.getStatus() != EventStatus.APPROVED) {
            throw new AccessDeniedException("You cannot view this event");
        }

        return mapToDTO(event);
    }


    @Override
    public EventResponseDTO approveEvent(Long eventId, String role) {
        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Only admins can approve events");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        event.setStatus(EventStatus.APPROVED);
        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO rejectEvent(Long eventId, String role, String comment) {

        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Only admins can reject");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setStatus(EventStatus.REJECTED);
        event.setAdminComment(comment);

        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public EventResponseDTO updateEvent(
            Long eventId,
            CreateEventRequestDTO request,
            String email,
            String role
    ) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Only creator or admin
        if (!role.equals("ADMIN") && !event.getCreatedByEmail().equals(email)) {
            throw new AccessDeniedException("You cannot edit this event");
        }

        // Only pending events editable
        if (event.getStatus() != EventStatus.PENDING) {
            throw new AccessDeniedException("Only pending events can be edited");
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setOrganizerName(request.getOrganizerName());
        event.setLocation(request.getLocation());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setIsPublic(request.getIsPublic());

        return mapToDTO(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(Long eventId, String email, String role) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (!role.equals("ADMIN") && !event.getCreatedByEmail().equals(email)) {
            throw new AccessDeniedException("You cannot delete this event");
        }

        if (event.getStatus() != EventStatus.PENDING) {
            throw new AccessDeniedException("Only pending events can be deleted");
        }

        eventRepository.delete(event);
    }

    @Override
    public void enrollInEvent(Long eventId, String email, String role) {

        if (!role.equals("STUDENT")) {
            throw new AccessDeniedException("Only students can enroll");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (event.getStatus() != EventStatus.APPROVED) {
            throw new AccessDeniedException("Event not approved");
        }

        if (enrollmentRepository.existsByStudentEmailAndEventId(email, eventId)) {
            throw new RuntimeException("Already enrolled");
        }

        Enrollment enrollment = Enrollment.builder()
                .studentEmail(email)
                .event(event)
                .build();

        enrollmentRepository.save(enrollment);
    }

    // 🔁 Mapper
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
