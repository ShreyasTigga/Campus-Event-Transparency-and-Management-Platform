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

    // Example: TECH_TALK, WORKSHOP, CULTURAL, SPORTS, STUDY_GROUP etc.
    private String category;

    // Who created the event (later will link to User)
    private String organizerName;

    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    // Whether event is visible to all
    @JsonProperty("isPublic")      // <--- important
    @Builder.Default
    private Boolean isPublic = true;   // <--- use wrapper Boolean, not primitive
}
