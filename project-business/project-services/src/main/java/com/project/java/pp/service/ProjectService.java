package com.project.java.pp.service;

import com.project.java.pp.common.enums.ProjectStatus;
import com.project.java.pp.common.enums.Role;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.dto.ProjectDto;
import com.project.java.pp.repository.filters.ProjectFilter;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ProjectService {

    public List<ProjectDto> getAll();
    public ProjectDto findById(Long id);

    public ProjectDto create(ProjectDto dto);
    public ProjectDto update(Long id,ProjectDto dto);
    public ProjectDto updateStatus(Long id, ProjectStatus status);
    public List<ProjectDto> search(ProjectFilter filter);
    public List<MemberDto> getAllMemberFromProject(Long projectId);
    @Transactional
    public List<MemberDto> addMember(Long prId, Long membId, Role role);
    @Transactional
    public List<MemberDto> deleteMember(Long prId,Long memId);
    public boolean isProjectExist(Long id);
}
