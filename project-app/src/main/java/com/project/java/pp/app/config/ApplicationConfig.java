package com.project.java.pp.app.config;

import com.project.java.pp.mapping.config.MappingConfig;
import com.project.java.pp.repository.config.RepositoryConfig;
import com.project.java.pp.service.config.ServiceConfig;
import com.project.java.pp.web.config.ControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RepositoryConfig.class,
        MappingConfig.class,
        ServiceConfig.class,
        ControllerConfig.class
})
public class ApplicationConfig {
}
