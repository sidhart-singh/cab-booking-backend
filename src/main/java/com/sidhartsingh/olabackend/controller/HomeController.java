package com.sidhartsingh.olabackend.controller;

import com.sidhartsingh.olabackend.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<MessageResponse> homeController() {
        return new ResponseEntity<>(new MessageResponse("Welcome to OLA Backend."), HttpStatus.OK);
    }
}
