package com.example.proj.servcie.student;

import com.example.proj.model.student.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Student createStudent(Student student);
}
