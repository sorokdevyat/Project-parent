package com.project.java.pp.repository.jpa;

import com.project.java.pp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
}
