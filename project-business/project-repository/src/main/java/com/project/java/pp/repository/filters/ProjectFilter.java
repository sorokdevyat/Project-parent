package com.project.java.pp.repository.filters;

import com.project.java.pp.common.enums.ProjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProjectFilter {
    private Long id;
    private String title;
    private List<ProjectStatus> statuses;
}
