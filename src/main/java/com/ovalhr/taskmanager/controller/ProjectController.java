package com.ovalhr.taskmanager.controller;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.repositories.ProjectRepository;
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
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page> paginationList(@RequestParam( value = "pageNumber", required = false) Integer pageNumber) {
        if(pageNumber== null) {
            pageNumber = 1;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 20);
        Page<Project> currentResults = projectRepository.findAll(pageRequest);
        return new ResponseEntity<Page>(currentResults, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Project> create(@Valid @RequestBody Project project) {
        project = projectRepository.save(project);
       return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Optional<Project>> edit(@PathVariable("id") Long id) {
        return new ResponseEntity<Optional<Project>>(projectRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            Optional<Project> project =  projectRepository.findById(id);
            if(project.isEmpty()) {
                return new ResponseEntity<String>("Object with given id Not Found", HttpStatus.BAD_REQUEST);
            } else {
                projectRepository.delete(project.get());
            }
            return new ResponseEntity<String>("Project With given id Deleted Successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Failed to delete", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/find-all")
    public ResponseEntity<List<Project>> findAll() {
        List<Project> projectList = (List<Project>) projectRepository.findAll();
        return new ResponseEntity<List<Project>>(projectList, HttpStatus.OK);
    }

}
