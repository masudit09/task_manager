package com.ovalhr.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by rana on 8/27/21.
 */
@NoRepositoryBean
public interface PagingAndSortingAndSpecificationExecutorRepository<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Modifying
    @Transactional
    @Query("DELETE from #{#entityName} e where e.id = :id")
    void deleteById(@Param("id") Long id);
}