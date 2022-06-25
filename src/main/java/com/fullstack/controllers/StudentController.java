package com.fullstack.controllers;

import com.fullstack.models.Student;
import com.fullstack.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // get all student data
    @GetMapping(path = "")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // register new student
    @PostMapping(path = "")
    public Student addStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // edit student data
    @PutMapping(path = "/{studentId}")
    public Student editStudent(
            @PathVariable(name = "studentId") Long id,
            @Valid @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }

    // delete student
    @DeleteMapping(path = "/{studentId}")
    public void deleteStudent(@PathVariable(name = "studentId") Long id) {
        studentService.deleteStudent(id);
    }


}
