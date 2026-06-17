package com.deepak.project.lovable_clone.repository;

import com.deepak.project.lovable_clone.entity.Project;
import com.deepak.project.lovable_clone.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT p as project, pm.role as role
            FROM Project p
            JOIN ProjectMember pm ON pm.project.id = p.id
            WHERE pm.user.id = :userId
              AND p.deletedAt IS NULL
            ORDER BY p.updatedAt DESC
            """)
    List<ProjectWithRole> findAllAccessibleByUser(@Param("userId") Long userId);



    @Query("""
    SELECT p from Project p  
    where p.id= :projectId 
    AND p.deletedAt IS NULL
    AND EXISTS( SELECT 1 from
            ProjectMember pm
            where pm.id.userId=:userId
            AND pm.id.projectId= p.id) 
""")
   Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId, @Param("userId") Long userId);


    @Query("""
            SELECT p as project, pm.role as role
            FROM Project p
            JOIN ProjectMember pm ON pm.project.id = p.id
            WHERE p.id = :projectId
              AND pm.user.id = :userId
              AND p.deletedAt IS NULL
            """)
    Optional<ProjectWithRole> findAccessibleProjectByIdWithRole(@Param("projectId") Long projectId,
                                                                @Param("userId") Long userId);

    interface ProjectWithRole {
        Project getProject();
        ProjectRole getRole();
    }
}