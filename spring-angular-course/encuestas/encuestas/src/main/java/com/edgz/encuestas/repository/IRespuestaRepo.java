package com.edgz.encuestas.repository;

import com.edgz.encuestas.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRespuestaRepo extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findByPreguntaId(Long preguntaId);
}
