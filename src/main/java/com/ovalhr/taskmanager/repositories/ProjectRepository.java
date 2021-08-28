package com.ovalhr.taskmanager.repositories;

import com.ovalhr.taskmanager.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
/**
 * Created by rana on 8/27/21.
 */
public interface ProjectRepository extends PagingAndSortingAndSpecificationExecutorRepository<Project> {

}
