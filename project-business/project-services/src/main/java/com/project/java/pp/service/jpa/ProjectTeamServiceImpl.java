package com.project.java.pp.service.jpa;

import com.project.java.pp.common.enums.Role;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.mapping.MemberMapper;
import com.project.java.pp.model.Member;
import com.project.java.pp.model.ProjectTeam;
import com.project.java.pp.repository.jpa.ProjectTeamRepository;
import com.project.java.pp.service.ProjectTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectTeamServiceImpl implements ProjectTeamService {
    private final ProjectTeamRepository projectTeamRepository;
    private final MemberMapper mapper;
    @Override
    public ProjectTeam addMember(Long projectId, Long memberId, Role role) {
        ProjectTeam projectTeam = new ProjectTeam(projectId,memberId,role);

        return projectTeamRepository.save(projectTeam);
    }

    @Override
    public List<MemberDto> allMembersInProject(Long projectId) {
        List<Member> byProjectId = projectTeamRepository.findByProjectId(projectId);
        return byProjectId.stream().map(m-> mapper.fromMemberToDto(m)).toList();
    }

    @Override
    public ProjectTeam deleteMember(Long projectId, Long memberId) {
        ProjectTeam projectTeam = new ProjectTeam(projectId,memberId);
        projectTeamRepository.deleteById(projectTeam.getId());
        return projectTeam;
    }

    @Override
    public boolean isMemberInProject(Long projectId, Long memberId) {
        ProjectTeam projectTeam = new ProjectTeam(projectId, memberId);
        return projectTeamRepository.findById(projectTeam.getId()).isPresent();
    }
}
