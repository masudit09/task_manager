package com.ovalhr.taskmanager.controller;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.entity.Task;
import com.ovalhr.taskmanager.repositories.TaskRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by rana on 8/27/21.
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page> paginationList(@RequestParam( value = "pageNumber", required = false) Integer pageNumber) {
        if(pageNumber== null) {
            pageNumber = 1;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 20);
        Page<Task> currentResults = taskRepository.findAll(pageRequest);
        return new ResponseEntity<Page>(currentResults, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        task = taskRepository.save(task);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Optional<Task>> edit(@PathVariable("id") Long id) {
        return new ResponseEntity<Optional<Task>>(taskRepository.findById(id), HttpStatus.OK);
    }

    @RequestMapping("/find-all")
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

}
