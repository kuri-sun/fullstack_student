package com.fullstack.services;

import com.fullstack.models.Student;
import com.fullstack.repos.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // return all students data.
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // register new student to database.
    public Student addStudent(Student student) {
        // check if email is already taken or not.
        Student foundStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (foundStudent != null) throw new IllegalStateException("That email has already taken.");

        // save to the database.
        return studentRepository.save(student);
    }

    // register new student to database.
    public Student editStudent(Long id, Student student) {
        // check if email is already taken or not.
        Student findStudent = studentRepository.findStudentById(id);
        if (findStudent == null) throw new IllegalStateException("User not found in the database.");

        // set new values
        findStudent.setName(student.getName());
        findStudent.setEmail(student.getEmail());
        findStudent.setGender(student.getGender());

        // save to the database.
        return studentRepository.save(findStudent);
    }

}
