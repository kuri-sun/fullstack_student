package com.fullstack.services;

import com.fullstack.models.Gender;
import com.fullstack.models.Student;
import com.fullstack.repos.StudentRepository;
import org.assertj.core.api.BDDAssumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService underTest;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAlStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student(
                "Test",
                "test@email.com",
                Gender.MALE
        );
        given(studentRepository.findStudentByEmail(anyString())).willReturn(null);

        // when
        underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals(capturedStudent, student);
    }

    @Test
    void canEditStudent() {
        // given
        Student student = new Student(
                "Test",
                "test@email.com",
                Gender.MALE
        );
        given(studentRepository.findStudentById(anyLong())).willReturn(student);
        given(studentRepository.findStudentByEmail(anyString())).willReturn(null);

        // when
        underTest.editStudent(1L, student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals(capturedStudent, student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
        given(studentRepository.findStudentByEmail(anyString())).willReturn(student);

        // when
        assertThrows(IllegalStateException.class, () -> {
            underTest.addStudent(student);
        });
        // then
        verify(studentRepository, never()).save(student);
    }

    @Test
    void canDeleteStudent() {
        // given
        long id = 10;
        Student student = new Student(
                "Jamila",
                "jamila@gmail.com",
                Gender.FEMALE
        );
        given(studentRepository.findStudentById(id)).willReturn(student);

        // when
        underTest.deleteStudent(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        // given
        long id = 10;
        given(studentRepository.findStudentById(id)).willReturn(null);

        // when
        assertThrows(IllegalStateException.class, () -> {
            underTest.deleteStudent(id);
        });
        // then
        verify(studentRepository, never()).deleteById(ArgumentMatchers.any());
    }


}