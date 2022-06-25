package com.fullstack.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "health")
public class HealthController {

    @GetMapping
    public String getAppHealth() {
        return "FullStack Student App Is Healthy.";
    }

}
