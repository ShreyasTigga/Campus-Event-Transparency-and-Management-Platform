package com.campusevents.backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequestDTO {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String organizerName;

    @NotBlank
    private String location;

    @NotNull
    @Future
    private LocalDateTime startTime;

    @NotNull
    @Future
    private LocalDateTime endTime;

    @Builder.Default
    private Boolean isPublic = true;
}
