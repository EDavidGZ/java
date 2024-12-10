package com.edgz.encuestas.repository;

import com.edgz.encuestas.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEncuestaRepo extends JpaRepository<Encuesta, Long> {
}
