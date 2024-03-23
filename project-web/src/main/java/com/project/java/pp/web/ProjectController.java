package com.project.java.pp.web;

import com.project.java.pp.common.enums.ProjectStatus;
import com.project.java.pp.common.enums.Role;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.dto.ProjectDto;
import com.project.java.pp.repository.filters.ProjectFilter;
import com.project.java.pp.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @Operation(summary = "Get all projects")
    @GetMapping(value = "",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDto> getAll(){
        List<ProjectDto> all = projectService.getAll();
        return all;
    }
    @Operation(summary = "Get project by id")
    @GetMapping(value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDto findById(@PathVariable Long id){
        ProjectDto byId = projectService.findById(id);
        return byId;
    }

    @Operation(summary = "Create a new project")
    @PostMapping(value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDto create(@RequestBody ProjectDto dto){
        ProjectDto projectDto = projectService.create(dto);
        return projectDto;
    }
    @Operation(summary = "Update project`s fields by his id")
    @PutMapping(value = "/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDto update(@PathVariable Long id,@RequestBody ProjectDto dto){
        ProjectDto updated = projectService.update(id, dto);
        return updated;
    }
    @Operation(summary = "Update status of project requesting his id and new status")
    @PutMapping(value = "/{id}/status",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDto updateStatus(@PathVariable Long id,
                                   @RequestParam ProjectStatus status){
        ProjectDto projectDto = projectService.updateStatus(id, status);
        return projectDto;
    }
    @Operation(summary = "Filter search projects")
    @GetMapping(value = "/search",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectDto> search(@RequestBody ProjectFilter filter){
        List<ProjectDto> search = projectService.search(filter);
        return search;
    }

    // project + member
    @Operation(summary = "Return all members in that project")
    @GetMapping(value = "/{projectId}/team",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDto> getAllFromProject(@PathVariable Long projectId){
        return projectService.getAllMemberFromProject(projectId);
    }
    @Operation(summary = "Add member to project")
    @PostMapping(value = "{projectId}/team",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDto> addMemberToProject(@PathVariable Long projectId,
                                              @RequestParam Long memberId,
                                              @RequestParam Role role){
        projectService.addMember(projectId,memberId,role);
        return getAllFromProject(projectId);
    }




}
