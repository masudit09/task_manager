package com.ovalhr.taskmanager.web.resource;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.mapper.Response;
import com.ovalhr.taskmanager.repositories.ProjectRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
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
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> update(@Valid @RequestBody Project newProject, @PathVariable Long id) {
        if(Util.hasRole("ROLE_ADMIN")) {
            return projectRepository.findById(id) .map(project -> {
                project.setName(newProject.getName());
                return new ResponseEntity<Response>(new Response(projectRepository.save(project)), HttpStatus.OK);
            }).orElseGet(() -> {
                return new ResponseEntity<Response>(new Response("Project Not found of don't have access with given id.", null), HttpStatus.OK);
            });
        } else {
            return projectRepository.findOwnProjectById(id, Util.getCurrentUsername()).map(project -> {
                project.setName(newProject.getName());
                return new ResponseEntity<Response>(new Response(projectRepository.save(project)), HttpStatus.OK);
            }).orElseGet(() -> {
                return new ResponseEntity<Response>(new Response("Project Not found of don't have access with given id.", null), HttpStatus.OK);
            });
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Response> edit(@PathVariable("id") Long id) {
        try {
            Optional<Project> project;
            if(Util.hasRole("ROLE_ADMIN")) {
                project =  projectRepository.findById(id);
            } else {
                project =  projectRepository.findOwnProjectById(id, Util.getCurrentUsername());
            }
            if(project.isEmpty()) {
                return new ResponseEntity<Response>(new Response("Object with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<Response>(new Response(project.get()), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Response>(new Response("Failed to get Object", null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            Optional<Project> project;
            if(Util.hasRole("ROLE_ADMIN")) {
                project =  projectRepository.findById(id);
            } else {
                project =  projectRepository.findOwnProjectById(id, Util.getCurrentUsername());
            }
            if(project.isEmpty()) {
                return new ResponseEntity<Response>(new Response("Object with given id Not Found or don't have access", null), HttpStatus.BAD_REQUEST);
            } else {
                projectRepository.delete(project.get());
            }
            return new ResponseEntity<Response>(new Response(project.get()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Response>(new Response("Failed to delete", null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/find-all")
    public ResponseEntity<Response> findAll() {
        List<Project> projectList;
        if(Util.hasRole("ROLE_ADMIN")) {
            projectList =  (List<Project>) projectRepository.findAll();
        } else {
            projectList =  (List<Project>) projectRepository.findAllByUserName(Util.getCurrentUsername());
        }
        return new ResponseEntity<Response>(new Response(projectList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/find-all/by-user/{username}")
    public ResponseEntity<Response> findAllByUser(@PathVariable("username") String username) {
        List<Project> projectList = (List<Project>) projectRepository.findAllByUserName(username);
        return new ResponseEntity<Response>(new Response(projectList), HttpStatus.OK);
    }

}
