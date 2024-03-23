package com.project.java.pp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.pp.common.enums.ProjectStatus;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private ProjectStatus status;
}
