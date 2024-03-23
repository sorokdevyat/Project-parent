package com.project.java.pp.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.project.java.pp.model"})
@EnableJpaRepositories(basePackages = {"com.project.java.pp.repository"})
public class RepositoryConfig {
}
