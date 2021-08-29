package com.ovalhr.taskmanager.web.resource;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.entity.Task;
import com.ovalhr.taskmanager.enumeration.TaskStatus;
import com.ovalhr.taskmanager.mapper.Response;
import com.ovalhr.taskmanager.repositories.TaskRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> update(@Valid @RequestBody Task newTask, @PathVariable Long id) {
        return taskRepository.findById(id) .map(task -> {
            newTask.setId(task.getId());
            return new ResponseEntity<Response>(new Response(taskRepository.save(newTask)), HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<Response>(new Response("Task Not found with given id.", null), HttpStatus.OK);
        });
    }

    @RequestMapping("/find-all")
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @RequestMapping("/find-all/project/{projectId}")
    public ResponseEntity<List<Task>> findAll(@PathVariable("projectId") Long projectId) {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @RequestMapping("/find-all/expired")
    public ResponseEntity<List<Task>> findAllExpired() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/find-all/by-user/{username}")
    public ResponseEntity<List<Task>> findAllByUser(@PathVariable("username") String username) {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
    }

}
