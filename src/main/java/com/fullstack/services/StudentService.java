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
        Student foundStudent = studentRepository.findStudentById(id);
        if (foundStudent == null) throw new IllegalStateException("User not found in the database.");

        // set new values
        foundStudent.setName(student.getName());
        foundStudent.setEmail(student.getEmail());
        foundStudent.setGender(student.getGender());

        // save to the database.
        return studentRepository.save(foundStudent);
    }

    // delete a student from database
    public void deleteStudent(Long id) {
        Student foundStudent = studentRepository.findStudentById(id);
        if (foundStudent == null) throw new IllegalStateException("User not found in the database.");

        studentRepository.deleteById(id);
    }


}
