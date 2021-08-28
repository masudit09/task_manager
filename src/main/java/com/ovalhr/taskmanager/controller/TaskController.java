package com.ovalhr.taskmanager.controller;

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
//        Task task = (Task) taskRepository.findById(id).get();
//        if(task.isPresent())
//        new ResponseEntity<>( task,  HttpStatus.OK))
//                .orElse(new ResponseEntity<Task>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<Optional<Task>>(taskRepository.findById(id), HttpStatus.OK);

//       Optional<Task> task = taskRepository.findById(id);
//       if(task.isEmpty()){
//           return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
//       } else {
//           return new ResponseEntity<Task>(task.get(), HttpStatus.OK);
//       }
    }
//
//    @RequestMapping("/find-all")
//    public ResponseEntity<List<Task>> findAll(@RequestParam(value = "division", required = false) Long division) {
//
//        List<Task> taskList;
//        if (Util.hasRole("ROLE_DIV_COM")) {
//            User user = userRepository.findByUsername(Util.getCurrentUsername());
//            division = user.getDivision().getId();
//            taskList = (List<Task>) taskRepository.findByDivision(division);
//        } else if (Util.hasRole("ROLE_DC")) {
//            taskList = new ArrayList<Task>();
//            User user = userRepository.findByUsername(Util.getCurrentUsername());
//            taskList.add(user.getTask());
//        } else {
//            if (division != null) {
//                taskList = (List<Task>) taskRepository.findByDivision(division);
//            } else {
//                taskList = (List<Task>) taskRepository.findAll();
//            }
//        }
//
//        return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);
//    }

}
