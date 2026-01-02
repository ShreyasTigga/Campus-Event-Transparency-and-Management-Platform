package com.campusevents.backend.dto;

import com.campusevents.backend.model.EventStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String organizerName;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isPublic;
    private EventStatus status;
    private String createdByEmail;
}
