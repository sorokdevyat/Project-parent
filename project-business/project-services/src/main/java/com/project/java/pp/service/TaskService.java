package com.project.java.pp.service;


import com.project.java.pp.common.enums.TaskStatus;
import com.project.java.pp.dto.taskDto.BaseTaskDto;
import com.project.java.pp.dto.taskDto.ExtTaskDto;
import com.project.java.pp.repository.filters.TaskFilter;
import com.project.java.pp.model.ProjectTeam;
import java.util.List;

public interface TaskService {
    ExtTaskDto create(BaseTaskDto dto);

    List<BaseTaskDto> findAll();
    public List<BaseTaskDto> search(TaskFilter filter);
    public ExtTaskDto updateStatus(TaskStatus status,Long id);
    public ExtTaskDto update(Long id,BaseTaskDto dto);

}
