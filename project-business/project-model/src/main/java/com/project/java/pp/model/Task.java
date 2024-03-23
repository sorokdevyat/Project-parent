package com.project.java.pp.model;

import com.project.java.pp.common.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Member executor;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "labor_hours")
    private Long laborHours;
    @Column(name = "deadline")
    private OffsetDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;
    @Column(name = "last_update_date")
    private OffsetDateTime lastUpdateDate;
}
