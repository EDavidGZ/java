package com.edgz.encuestas.services;

import com.edgz.encuestas.model.Respuesta;

import java.util.List;
import java.util.Optional;

public interface IRespuestasService {

    Respuesta agregarRespuestaAPregunta(Long preguntaId, String contenido);

    List<Respuesta> obtenerRespuestaPorPregunta(Long preguntaId);

    Optional<Respuesta> obtenerDetallesRespuesta(Long respuestaId);

    Respuesta actualizarRespuesta(Long respuestaId, String nuevoContenido);

    void eliminarRespuesta(Long respuestaId);
}
