package com.project.java.pp.repository.jpa;


import com.project.java.pp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long>, JpaSpecificationExecutor<Project> {
    @Override
    Optional<Project> findById(Long aLong);

    Optional<Project> findProjectByTitle(String title);
}
