package com.project.java.pp.repository.jpa;

import com.project.java.pp.model.Member;
import com.project.java.pp.model.ProjectTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeam.PK>{
    @Query(value = """
            select m
            from Member m
            join ProjectTeam pt on m.id = pt.id.member.id
            join Project p on p.id = pt.id.project.id
            where p.id = :projectId and m.status <> 'DELETED'
            """)
    List<Member> findByProjectId(@Param("projectId") Long id);
}
