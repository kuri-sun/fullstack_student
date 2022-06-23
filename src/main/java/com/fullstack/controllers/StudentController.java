package com.fullstack.controllers;

import com.fullstack.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.fullstack.models.Gender.FEMALE;
import static com.fullstack.models.Gender.MALE;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    @GetMapping(path = "")
    public List<Student> getAllStudents() {
        List<Student> students = Arrays.asList(
                new Student(1L, "Jamila", "jlksfjsslkdfdl", FEMALE),
                new Student(2L, "Alex", "jlksfjsslkdfdl", MALE)
        );
        return students;
    }

}
