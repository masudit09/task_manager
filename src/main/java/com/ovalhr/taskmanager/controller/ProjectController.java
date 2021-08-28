package com.ovalhr.taskmanager.controller;

import com.ovalhr.taskmanager.entity.Project;
import com.ovalhr.taskmanager.repositories.ProjectRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
//
//    @RequestMapping("/find-all")
//    public ResponseEntity<List<Project>> findAll(@RequestParam(value = "division", required = false) Long division) {
//
//        List<Project> projectList;
//        if (Util.hasRole("ROLE_DIV_COM")) {
//            User user = userRepository.findByUsername(Util.getCurrentUsername());
//            division = user.getDivision().getId();
//            projectList = (List<Project>) projectRepository.findByDivision(division);
//        } else if (Util.hasRole("ROLE_DC")) {
//            projectList = new ArrayList<Project>();
//            User user = userRepository.findByUsername(Util.getCurrentUsername());
//            projectList.add(user.getProject());
//        } else {
//            if (division != null) {
//                projectList = (List<Project>) projectRepository.findByDivision(division);
//            } else {
//                projectList = (List<Project>) projectRepository.findAll();
//            }
//        }
//
//        return new ResponseEntity<List<Project>>(projectList, HttpStatus.OK);
//    }

}
