package com.ovalhr.taskmanager.controller;

import com.ovalhr.taskmanager.entity.Authority;
import com.ovalhr.taskmanager.entity.User;
import com.ovalhr.taskmanager.enumeration.Role;
import com.ovalhr.taskmanager.mapper.Response;
import com.ovalhr.taskmanager.repositories.AuthorityRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by rana on 8/28/21.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @RequestMapping(value = "/sign-up",  method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User exUser = userRepository.findByUsername(user.getUsername());
        if (exUser == null) {
            Role role = user.getRole();
            user = userRepository.save(user);
            Authority authority = new Authority(user.getUsername(), role.getText());
            authorityRepository.save(authority);
            return new ResponseEntity<Response>(new Response("User Successfully Created.", user), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response("User Already Exist.", user) , HttpStatus.BAD_REQUEST);
        }

    }
}
