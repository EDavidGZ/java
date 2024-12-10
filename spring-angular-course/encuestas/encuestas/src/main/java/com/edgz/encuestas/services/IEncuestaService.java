package com.edgz.encuestas.services;

import com.edgz.encuestas.model.Encuesta;

import java.util.List;
import java.util.Optional;

public interface IEncuestaService {

    Encuesta crearEncuesta(String titulo);

    List<Encuesta> obtenerTodasLasEncuestas();

    Optional<Encuesta> obtenerDetallesEncuesta(Long encuestaId);

    Encuesta actualizarEncuesta(Long encuestaId, String nuevoTitulo);

    void eliminarEncuesta(Long encuestaId);
}
