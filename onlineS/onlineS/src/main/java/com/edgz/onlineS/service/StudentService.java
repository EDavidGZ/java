package com.edgz.onlineS.service;

import com.edgz.onlineS.entities.Student;
import com.edgz.onlineS.repositories.IStudentRepo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentService {

    private final IStudentRepo studentRepo;

    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}
