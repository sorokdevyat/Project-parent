package com.project.java.pp.dto.taskDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.pp.common.enums.TaskStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// Когда выборка состоит из множетсва задач и передаваемый json нужно упростить, в этом случае передаются только id проекта, работника и автора
// Базовый дто.
public class BaseTaskDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private Long laborHours;
    private OffsetDateTime deadline;
    private TaskStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long authorId;
    private Long executorId;
    private Long projectId;
    private OffsetDateTime creationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastUpdateDate;
}
