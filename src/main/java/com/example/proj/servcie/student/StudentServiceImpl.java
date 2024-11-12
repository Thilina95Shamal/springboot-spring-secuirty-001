package com.example.proj.servcie.student;

import com.example.proj.model.student.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(List.of(
                new Student("1","name1",21),
                new Student("2","name2",22),
                new Student("3","name3",23)));
    }

    @Override
    public Student createStudent(Student student) {
        return student;
    }
}
