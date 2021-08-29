package com.ovalhr.taskmanager.repositories;

import com.ovalhr.taskmanager.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
/**
 * Created by rana on 8/27/21.
 */
public interface ProjectRepository extends PagingAndSortingAndSpecificationExecutorRepository<Project> {
    @Query("SELECT x FROM Project x WHERE x.createdBy = :username AND x.id=:id")
    public Optional<Project> findOwnProjectById(@Param("id") Long id, @Param("username") String username);

    @Query("SELECT x FROM Project x WHERE x.createdBy = :username")
    public List<Project> findAllByUserName(@Param("username") String username);
}
