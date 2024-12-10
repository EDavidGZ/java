package com.edgz.encuestas.repository;

import com.edgz.encuestas.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPreguntaRepo extends JpaRepository<Pregunta, Long> {

    List<Pregunta> findByEncuestaId(Long encuestaId);
}
