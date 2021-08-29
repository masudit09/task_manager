package com.ovalhr.taskmanager.service;

import com.ovalhr.taskmanager.entity.Authority;
import com.ovalhr.taskmanager.entity.User;
import com.ovalhr.taskmanager.enumeration.Role;
import com.ovalhr.taskmanager.repositories.AuthorityRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SystemUserCreator implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setFullName("admin");
            user.setPlainPassword("admin");
            user.setEnabled(true);
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);

            Authority authority = new Authority();
            authority.setAuthority(Role.ROLE_ADMIN.toString());
            authority.setUsername("admin");
            authorityRepository.save(authority);
        } else {
            System.out.println("Admin User Already Exist");
        }
        user = userRepository.findByUsername("user");
        if (user == null) {
            user = new User();
            user.setUsername("user");
            user.setFullName("user");
            user.setPlainPassword("user");
            user.setEnabled(true);
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);

            Authority authority = new Authority();
            authority.setAuthority(Role.ROLE_USER.toString());
            authority.setUsername("user");
            authorityRepository.save(authority);
        } else {
            System.out.println("user Already Exist");
        }
    }


}
