package com.project.java.pp.service.jpa;

import com.project.java.pp.common.enums.ProjectStatus;
import com.project.java.pp.common.enums.Role;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.dto.ProjectDto;
import com.project.java.pp.repository.filters.ProjectFilter;
import com.project.java.pp.mapping.ProjectMapper;
import com.project.java.pp.model.Project;
import com.project.java.pp.repository.jpa.ProjectRepository;
import com.project.java.pp.repository.specifications.ProjectSpecification;
import com.project.java.pp.service.MemberService;
import com.project.java.pp.service.ProjectService;
import com.project.java.pp.service.ProjectTeamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;
    private final MemberService memberService;
    private final ProjectTeamService projectTeamService;

    public List<ProjectDto> getAll(){
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(p -> mapper.fromProjectToDto(p)).toList();
    }
    public ProjectDto findById(Long id){
        Optional<Project> byId = projectRepository.findById(id);
        Project project = byId.orElse(new Project());

        return mapper.fromProjectToDto(project);

    }

    @Transactional
    public ProjectDto create(ProjectDto dto){

        Project project = mapper.fromDtoToProject(dto);
        // Проверка на уникальность по названию, тк я не понял зачем проверка на
        // уникальность по id;
        if (projectRepository.findProjectByTitle(dto.getTitle()).isPresent()){
            throw new DuplicateKeyException(String.format("Project with title = %s already exists", project.getTitle()));        }
        project.setStatus(ProjectStatus.DRAFT);

        Project saved = projectRepository.save(project);
        addMember(saved.getId(),5l, Role.ADMIN);
        log.info("Create project with id = {}",saved.getId());
        return mapper.fromProjectToDto(saved);
    }
    @Transactional
    public ProjectDto update(Long id,ProjectDto dto){
        if(projectRepository.findById(id).isEmpty()){
            throw new NoSuchElementException(String.format("Ele,ent with id = %d does not exist",id));
        }

        Project project = mapper.fromDtoToProject(dto);
        project.setId(id);
        Project updated = projectRepository.save(project);
        log.info("Update project with id = {}",updated.getId());
        return mapper.fromProjectToDto(updated);
    }
    @Transactional
    public ProjectDto updateStatus(Long id,ProjectStatus status){

        if (projectRepository.findById(id).isEmpty()){
            throw new NoSuchElementException(String.format("Element with id = %d does not exist",id));
        }
        Project project = projectRepository.findById(id).get();
        project.setStatus(status);
        Project updatedStatus = projectRepository.save(project);
        log.info("Update status of project with id = {}, new Status is: {}",id,status);
        return mapper.fromProjectToDto(updatedStatus);
    }

    public List<ProjectDto> search(ProjectFilter filter){
        List<Project> all = projectRepository.findAll(ProjectSpecification.getSpec(filter));
        return all.stream().map(p-> mapper.fromProjectToDto(p)).toList();
    }


    public List<MemberDto> getAllMemberFromProject(Long projectId){
        return projectTeamService.allMembersInProject(projectId);
    }
    @Transactional
    public List<MemberDto> addMember(Long prId, Long membId, Role role){
        if (!isProjectExist(prId)){
            throw new NoSuchElementException(String.format("Project with id = %d is not exist",prId));
        }
        if (!memberService.isExist(membId)){
            throw new NoSuchElementException(String.format("Member with id = %d is not exist",membId));
        }
        projectTeamService.addMember(prId,membId,role);
        log.info("Member with id = {} added to project with id = {}",membId, prId);
        return getAllMemberFromProject(prId);
    }
    @Transactional
    public List<MemberDto> deleteMember(Long prId,Long memId){
        projectTeamService.deleteMember(prId,memId);
        log.info("delete member with id = {}, from project with id = {}",memId,prId);
        return getAllMemberFromProject(prId);
    }

    public boolean isProjectExist(Long projectId){
        return projectRepository.findById(projectId).isPresent();
    }
}
