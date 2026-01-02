package com.campusevents.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String category;

    private String organizerName;

    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder.Default
    private Boolean isPublic = true;

    // 🔐 Who created the event (from JWT)
    @Column(nullable = false)
    private String createdByEmail;

    // 🟡 Approval workflow
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EventStatus status = EventStatus.PENDING;
}
