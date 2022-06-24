package com.fullstack.repos;

import com.fullstack.models.Gender;
import com.fullstack.models.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindStudentById() {
        // given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
        Student savedStudent = underTest.save(student);

        // when
        Student foundStudent = underTest.findStudentById(savedStudent.getId());

        // then
        assertNotNull(foundStudent);
    }

    @Test
    void shouldNotFindStudentById() {
        // given
        // when
        Student foundStudent = underTest.findStudentById(1L);

        // then
        assertNull(foundStudent);
    }


    @Test
    void shouldFindStudentByEmail() {
        // given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
        Student savedStudent = underTest.save(student);

        // when
        Student foundStudent = underTest.findStudentByEmail(savedStudent.getEmail());

        // then
        assertNotNull(foundStudent);
    }


    @Test
    void shouldNotFindStudentByEmail() {
        // given
        String email = "jamila@gmail.com";

        // when
        Student foundStudent = underTest.findStudentByEmail(email);

        // then
        assertNull(foundStudent);
    }
}