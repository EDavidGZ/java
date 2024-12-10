package com.edgz.onlineS.repositories;

import com.edgz.onlineS.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectRepo extends JpaRepository<Subject, Long> {
}
