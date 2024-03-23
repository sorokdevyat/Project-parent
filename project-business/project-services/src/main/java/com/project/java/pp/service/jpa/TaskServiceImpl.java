package com.project.java.pp.service.jpa;


import com.project.java.pp.common.enums.TaskStatus;
import com.project.java.pp.common.exceptions.MemberIsNotAttachedToProject;
import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.dto.taskDto.BaseTaskDto;
import com.project.java.pp.dto.taskDto.ExtTaskDto;
import com.project.java.pp.repository.filters.TaskFilter;
import com.project.java.pp.mapping.MemberMapper;
import com.project.java.pp.mapping.TaskMapper;
import com.project.java.pp.model.Member;
import com.project.java.pp.repository.jpa.TaskRepository;
import com.project.java.pp.repository.specifications.TaskSpecification;
import com.project.java.pp.service.MemberService;
import com.project.java.pp.service.ProjectService;
import com.project.java.pp.model.Task;
import com.project.java.pp.service.ProjectTeamService;
import com.project.java.pp.service.TaskService;
import com.project.java.pp.service.notification.Notifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectTeamService projectTeamService;
    private final MemberService memberService;
    private final ProjectService projectService;
    private final TaskMapper taskMapper;
    private final MemberMapper memberMapper;
    private final Notifier notifier;

    public List<BaseTaskDto> search(TaskFilter filter){
        List<Task> tasks = taskRepository.findAll(TaskSpecification.getSpec(filter));
        return tasks.stream().map(t -> taskMapper.fromTaskToBaseDto(t)).toList();
    }

    // Проверка переданной задачи:
    public void validateTask(BaseTaskDto task){
        // 1. дата, когда задача должна быть исполнена. Нельзя выбрать дату если дата меньше, чем  дата создания + трудозатраты.
//        if (task.getCreationDate().isAfter(task.getDeadline().minusHours(task.getLaborHours()))) {
//            throw new ImposibleDeadlineException("Impossible deadline");
//        }
        // 2. Существует ли такой проект.
        Long projectId = task.getProjectId();
        if (!projectService.isProjectExist(projectId)){
            throw new NoSuchElementException(String.format("Project with id = %d is not present",projectId));
        }
        // 3. Существует ли такой мембер  + то что исполнитель также принадлжеит к проекту как и задача.
        if (!ObjectUtils.isEmpty(task.getExecutorId())) {
            Long executorId = task.getExecutorId();
            if (!memberService.isExist(executorId)) {
                throw new NoSuchElementException(String.format("Member with id = %d not exist", executorId));
            }

            if (!projectTeamService.isMemberInProject(projectId,executorId)){
                throw new MemberIsNotAttachedToProject(String.format("Member with id = %d is not present in project with id = %d",executorId
                        ,projectId));
            }
        }
    }
    // Тут должно работать так, чтобы автор был тот, кто создает задачу
    private Member getAuthor(BaseTaskDto task) {
        // Получаем мембера который отправляет запрос
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member author = memberMapper.fromDtoToMember(memberService.findByAccountUsername(authentication.getName()));
        if (ObjectUtils.isEmpty(author.getId())){
            throw new MemberIsNotAttachedToProject("Only authorized");
        }
        checkMemberInProject(task.getProjectId(),author.getId());
        return author;
    }
    // ПРоверка что мемебер в нужном проекте
    private void checkMemberInProject(Long projectId, Long memberId) {
        if (!projectTeamService.isMemberInProject(projectId, memberId)) {
            throw new MemberIsNotAttachedToProject(
                    String.format("Member with id = %d is not involved in project with id = %d",
                            memberId,
                            projectId)
            );
        }
    }

    @Override
    @Transactional
    public ExtTaskDto create(BaseTaskDto dto) {
        dto.setCreationDate(OffsetDateTime.now());
        dto.setLastUpdateDate(OffsetDateTime.now());
        validateTask(dto);

        Task task = taskMapper.fromTaskDtoToTask(dto);
        task.setStatus(TaskStatus.NEW);
        Member author = getAuthor(dto);
        task.setAuthor(author);

        sendNotify(task);

        Task createdTask = taskRepository.save(task);
        log.info("Create task with id = {}",createdTask.getId());
        return taskMapper.fromTaskToExtTaskDto(createdTask);
    }
    @Override
    @Transactional
    public ExtTaskDto update(Long id,BaseTaskDto taskDto){
        if (taskRepository.findById(id).isEmpty()){
            throw new NoSuchElementException(String.format("Task with id = %d does not exist",id));
        }
        validateTask(taskDto);
        Task task = taskMapper.fromTaskDtoToTask(taskDto);
        task.setId(id);
        task.setLastUpdateDate(OffsetDateTime.now());
        task.setAuthor(getAuthor(taskDto));
        Task updated = taskRepository.save(task);

        sendNotify(updated);
        log.info("Update task with id = {}",id);
        return taskMapper.fromTaskToExtTaskDto(updated);
    }
    @Override
    @Transactional
    public ExtTaskDto updateStatus(TaskStatus status,Long id){
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format("Task with id = %d not exist", id))
        );
        task.setStatus(status);
        task.setLastUpdateDate(OffsetDateTime.now());
        Task updated = taskRepository.save(task);
        log.info("Update status of task with id = {}, new status is : {}",id,status);
        return taskMapper.fromTaskToExtTaskDto(updated);
    }

    @Override
    public List<BaseTaskDto> findAll() {
        return taskRepository.findAll().stream().map(t -> taskMapper.fromTaskToBaseDto(t)).toList();
    }
    private void sendNotify(Task task){
        if (!ObjectUtils.isEmpty(task.getExecutor())){
            MemberDto executor = memberService.getById(task.getExecutor().getId());
            if (!ObjectUtils.isEmpty(executor.getEmail())){
                notifier.sendMessage(executor.getEmail(), executor.getFirstname() + " " + executor.getPosition());
            }
        }
    }

}
