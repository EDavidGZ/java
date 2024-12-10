package com.edgz.onlineS.repositories;

import com.edgz.onlineS.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepo extends JpaRepository<Student, Long> {
}
