package com.project.java.pp.mapping;
import com.project.java.pp.dto.ProjectDto;
import com.project.java.pp.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public Project fromDtoToProject(ProjectDto dto){
        Project project = Project.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
        return project;
    }
    public ProjectDto fromProjectToDto(Project project){
        ProjectDto dto = ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
        return dto;
    }
}
