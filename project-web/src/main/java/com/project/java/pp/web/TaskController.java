package com.project.java.pp.web;

import com.project.java.pp.common.enums.TaskStatus;
import com.project.java.pp.dto.taskDto.BaseTaskDto;
import com.project.java.pp.dto.taskDto.ExtTaskDto;
import com.project.java.pp.repository.filters.TaskFilter;
import com.project.java.pp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/tasks")
public class TaskController {
    private final TaskService taskService;
    @Operation(summary = "Get All base tasks")
    @GetMapping(value = "",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseTaskDto> findAll(){
        return taskService.findAll();
    }

    @Operation(summary = "Create a new task")
    @PostMapping(value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ExtTaskDto create(@RequestBody BaseTaskDto dto){
        return taskService.create(dto);
    }

    @Operation(summary = "Task Search filter")
    @PostMapping(value = "/search",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseTaskDto> search(@RequestBody TaskFilter filter){
        return taskService.search(filter);

    }
    @Operation(summary = "update status of task")
    @PutMapping(value = "{id}/status",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ExtTaskDto updateStatus(@RequestParam TaskStatus status, @PathVariable Long id){
        return taskService.updateStatus(status,id);
    }
    @Operation(summary = "Update specified fields of task")
    @PutMapping(value = "{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ExtTaskDto update(@RequestBody BaseTaskDto dto,@PathVariable Long id){
        return taskService.update(id,dto);
    }
}
