package com.project.java.pp.mapping;

import com.project.java.pp.dto.taskDto.BaseTaskDto;
import com.project.java.pp.dto.taskDto.ExtTaskDto;
import com.project.java.pp.model.Member;
import com.project.java.pp.model.Project;
import com.project.java.pp.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final MemberMapper memberMapper;
    private final ProjectMapper projectMapper;
    public Task fromTaskDtoToTask(BaseTaskDto taskDto){
        Task task = Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .lastUpdateDate(taskDto.getLastUpdateDate())
                .creationDate(taskDto.getCreationDate())
                .laborHours(taskDto.getLaborHours())
                .deadline(taskDto.getDeadline())
                .status(taskDto.getStatus())
                .build();
        // Далее нужно присвоить задаче информацию об исполнителе, авторе и проекте.

        // 1. Если taskDto был создан таким образом BaseTaskDto dto = new ExtTaskDto(). То соответсвенно, он имеет информацию о расширенных полях этого класса
        // поэтому в этом случае задаче мы сетим полную информацию о них.
        if (taskDto instanceof ExtTaskDto extTaskDto){
            task.setAuthor(memberMapper.fromDtoToMember(extTaskDto.getAuthor()));
            if (!ObjectUtils.isEmpty(taskDto.getExecutorId())){
                    task.setExecutor(memberMapper.fromDtoToMember(extTaskDto.getExecutor()));
            }
            task.setProject(projectMapper.fromDtoToProject(extTaskDto.getProject()));
        }
        // 2. Если же, taskDto не был создан таким образом, то он содержит информацию только о их айди, поэтому мы с помощью биледра заполняем информацию
        // только об их айди.

        else {
            task.setAuthor(Member.builder().id(taskDto.getAuthorId()).build());
            if (!ObjectUtils.isEmpty(taskDto.getExecutorId())){
                task.setExecutor(Member.builder().id(taskDto.getExecutorId()).build());
            }
            task.setProject(Project.builder().id(taskDto.getProjectId()).build());
        }
        return task;
    }
    public BaseTaskDto fromTaskToBaseDto(Task task){
        BaseTaskDto baseTaskDto = BaseTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .creationDate(task.getCreationDate())
                .deadline(task.getDeadline())
                .laborHours(task.getLaborHours())
                .lastUpdateDate(task.getLastUpdateDate())
                .status(task.getStatus())
                .authorId(task.getAuthor().getId())
                .projectId(task.getProject().getId())
                .build();
        // Проверка нужна т.к задача может пока не содержать исполнителей
        if (!ObjectUtils.isEmpty(task.getExecutor())){
            baseTaskDto.setExecutorId(task.getExecutor().getId());
        }
        return baseTaskDto;
    }
    public ExtTaskDto fromTaskToExtTaskDto(Task task){
        ExtTaskDto extTaskDto = ExtTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .creationDate(task.getCreationDate())
                .deadline(task.getDeadline())
                .laborHours(task.getLaborHours())
                .lastUpdateDate(task.getLastUpdateDate())
                .status(task.getStatus())
                .authorId(task.getAuthor().getId())
                .projectId(task.getProject().getId())
                .author(memberMapper.fromMemberToDto(task.getAuthor()))
                .project(projectMapper.fromProjectToDto(task.getProject()))
                .build();
        if (!ObjectUtils.isEmpty(task.getExecutor())){
            extTaskDto.setExecutorId(task.getExecutor().getId());
            extTaskDto.setExecutor(memberMapper.fromMemberToDto(task.getExecutor()));
        }
        return extTaskDto;
    }
}
