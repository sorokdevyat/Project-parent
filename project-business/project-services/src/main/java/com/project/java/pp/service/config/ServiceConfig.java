package com.project.java.pp.service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.project.java.pp.service")
@EnableConfigurationProperties(RabbitMQConfig.class)
public class ServiceConfig {
}
