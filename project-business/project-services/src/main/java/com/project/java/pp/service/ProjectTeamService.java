package com.project.java.pp.service;



import com.project.java.pp.common.enums.Role;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.model.ProjectTeam;

import java.util.List;

public interface ProjectTeamService {

    ProjectTeam addMember(Long projectId, Long memberId, Role role);
    List<MemberDto> allMembersInProject(Long projectId);

    ProjectTeam deleteMember(Long projectId,Long memberId);

    boolean isMemberInProject(Long projectId, Long memberId);
}
