package com.edgz.encuestas.services;

import com.edgz.encuestas.model.Pregunta;

import java.util.List;
import java.util.Optional;

public interface IPreguntaService {

    Pregunta agregarPreguntaAEncuesta(Long encuestaId, String contenido);

    List<Pregunta> obtenerPreguntaPorEncuesta(Long encuestaId);

    Optional<Pregunta> obtenerDetallesPregunta(Long preguntaId);

    Pregunta actualizarPregunta(Long preguntaId, String nuevoContenido, Long encuestaId);

    void eliminarPregunta(Long preguntaId);
}
