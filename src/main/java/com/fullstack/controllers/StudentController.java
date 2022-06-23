package com.fullstack.controllers;

import com.fullstack.models.Student;
import com.fullstack.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // get all student data.
    @GetMapping(path = "")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // register new student
    @PostMapping(path = "")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // edit student data
    @PutMapping(path = "/{studentId}")
    public Student editStudent(
            @PathVariable(name = "studentId") Long id,
            @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }


}
