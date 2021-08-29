package com.ovalhr.taskmanager.web.resource;

import com.ovalhr.taskmanager.entity.Task;
import com.ovalhr.taskmanager.enumeration.TaskStatus;
import com.ovalhr.taskmanager.mapper.Response;
import com.ovalhr.taskmanager.repositories.TaskRepository;
import com.ovalhr.taskmanager.util.Util;
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
    public ResponseEntity<Response> create(@Valid @RequestBody Task task) {
        try {
            task = taskRepository.save(task);
            return new ResponseEntity<Response>(new Response(task), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Response>(new Response("Failed to save Task."), HttpStatus.OK);
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Response> edit(@PathVariable("id") Long id) {
        try {
            Optional<Task> task;
            if(Util.hasRole("ROLE_ADMIN")) {
                task =  taskRepository.findById(id);
            } else {
                task =  taskRepository.findOwnTaskById(id, Util.getCurrentUsername());
            }
            if(task.isEmpty()) {
                return new ResponseEntity<Response>(new Response("Object with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<Response>(new Response(task.get()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Response>(new Response("Failed to get Object", null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> update(@Valid @RequestBody Task newTask, @PathVariable Long id) {
        Optional<Task> task;
        if(Util.hasRole("ROLE_ADMIN")) {
            task =  taskRepository.findById(id);
        } else {
            task =  taskRepository.findOwnTaskById(id, Util.getCurrentUsername());
        }
        if(task.isEmpty()) {
            return new ResponseEntity<Response>(new Response("Task with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
        } else {
            Task taskObject = task.get();
            if(TaskStatus.CLOSED.getText().equals(taskObject.getTaskStatus().getText())) {
                newTask.setId(taskObject.getId());
                newTask = taskRepository.save(newTask);
                return new ResponseEntity<Response>(new Response(newTask), HttpStatus.OK);
            } else {
                return new ResponseEntity<Response>(new Response("Closed Task can not be edited. ", null), HttpStatus.BAD_REQUEST);
            }

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            Optional<Task> task;
            if(Util.hasRole("ROLE_ADMIN")) {
                task =  taskRepository.findById(id);
            } else {
                task =  taskRepository.findOwnTaskById(id, Util.getCurrentUsername());
            }
            if(task.isEmpty()) {
                return new ResponseEntity<Response>(new Response("Task with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
            } else {
                taskRepository.delete(task.get());
            }
            return new ResponseEntity<Response>(new Response(task.get()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Response>(new Response("Task with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/find-all")
    public ResponseEntity<Response> findAll() {
        List<Task> taskList;
        if(Util.hasRole("ROLE_ADMIN")) {
            taskList = (List<Task>) taskRepository.findAll();
        } else {
            taskList = taskRepository.findAllByUserName(Util.getCurrentUsername());
        }
        return new ResponseEntity<Response>(new Response(taskList), HttpStatus.OK);
    }

    @RequestMapping("/find-all/by-project/{projectId}")
    public ResponseEntity<Response> findAll(@PathVariable("projectId") Long projectId) {
        List<Task> taskList;
        if(Util.hasRole("ROLE_ADMIN")) {
            taskList = taskRepository.findByProjectId(projectId);
        } else {
            taskList = taskRepository.findOwnTaskByProjectId(projectId, Util.getCurrentUsername());
        }
        return new ResponseEntity<Response>(new Response(taskList), HttpStatus.OK);
    }

    @RequestMapping("/find-all/by-status/{status}")
    public ResponseEntity<Response> findAll(@PathVariable("status") String status) {
       TaskStatus taskStatus = TaskStatus.valueOf(status);
       System.out.println(" Status object:==="+taskStatus);

        List<Task> taskList;
        if(Util.hasRole("ROLE_ADMIN")) {
            taskList = taskRepository.findByStatus(taskStatus);
        } else {
            taskList = taskRepository.findOwnTaskByStatus(taskStatus, Util.getCurrentUsername());
        }
        return new ResponseEntity<Response>(new Response(taskList), HttpStatus.OK);
    }

    @RequestMapping("/find-all/expired")
    public ResponseEntity<Response> findAllExpired() {
        List<Task> taskList;
        if(Util.hasRole("ROLE_ADMIN")) {
            taskList = taskRepository.findExpired();
        } else {
            taskList = taskRepository.findOwnExpiredTask(Util.getCurrentUsername());
        }
        return new ResponseEntity<Response>(new Response(taskList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/find-all/by-user/{username}")
    public ResponseEntity<Response> findAllByUser(@PathVariable("username") String username) {
        List<Task> taskList = taskRepository.findAllByUserName(username);
        return new ResponseEntity<Response>(new Response(taskList), HttpStatus.OK);
    }

}
