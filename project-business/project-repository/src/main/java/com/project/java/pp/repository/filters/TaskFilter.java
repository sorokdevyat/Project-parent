package com.project.java.pp.repository.filters;

import com.project.java.pp.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TaskFilter {
    private String title;
    private OffsetDateTime deadlineMin;
    private OffsetDateTime deadlineMax;
    private OffsetDateTime creationDateMin;
    private OffsetDateTime creationDateMax;
    private Long authorId;
    private Long executorId;
    private List<TaskStatus> statuses;
}
