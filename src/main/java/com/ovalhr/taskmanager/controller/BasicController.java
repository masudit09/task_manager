package com.ovalhr.taskmanager.controller;

import com.ovalhr.taskmanager.mapper.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rana on 8/22/21.
 */
@RestController
@RequestMapping("/api")
public class BasicController {

    @GetMapping(value = "/test",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> paginationList() {
        return new ResponseEntity<Response>(new Response(""), HttpStatus.OK);
    }
}
