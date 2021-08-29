package com.ovalhr.taskmanager.repositories;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.entity.Task;
import com.ovalhr.taskmanager.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by rana on 8/27/21.
 */
public interface TaskRepository extends PagingAndSortingAndSpecificationExecutorRepository<Task> {
    @Query("SELECT x FROM Task x WHERE x.createdBy = :username AND x.id=:id")
    public Optional<Task> findOwnTaskById(@Param("id") Long id, @Param("username") String username);

    @Query("SELECT x FROM Task x WHERE x.createdBy = :username AND x.project=:projectId")
    public List<Task> findOwnTaskByProjectId(@Param("projectId") Long projectId, @Param("username") String username);

    @Query("SELECT x FROM Task x WHERE x.project=:projectId")
    public List<Task> findByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT x FROM Task x WHERE x.createdBy = :username AND x.dueDate < CURRENT_DATE")
    public List<Task> findOwnExpiredTask(@Param("username") String username);

    @Query("SELECT x FROM Task x WHERE x.taskStatus=:status")
    public List<Task> findByStatus(@Param("status") TaskStatus status);

    @Query("SELECT x FROM Task x WHERE x.createdBy = :username AND x.taskStatus=:status")
    public List<Task> findOwnTaskByStatus(@Param("status") TaskStatus status, @Param("username") String username);

    @Query("SELECT x FROM Task x WHERE x.dueDate < CURRENT_DATE")
    public List<Task> findExpired();

    @Query("SELECT x FROM Task x WHERE x.createdBy = :username")
    public List<Task> findAllByUserName(@Param("username") String username);
}
