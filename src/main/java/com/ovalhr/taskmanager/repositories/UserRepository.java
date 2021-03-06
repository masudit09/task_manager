package com.ovalhr.taskmanager.repositories;

import com.ovalhr.taskmanager.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
/**
 * Created by rana on 8/27/21.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT x FROM User x WHERE x.username = :username ")
    public User findByUsername(@Param("username") String username);
	
}
