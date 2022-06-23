package com.fullstack.controllers;

import com.fullstack.models.Student;
import com.fullstack.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.fullstack.models.Gender.FEMALE;
import static com.fullstack.models.Gender.MALE;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(path = "")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }



}
