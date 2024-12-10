package com.edgz.encuestas.services.impl;

import com.edgz.encuestas.model.Pregunta;
import com.edgz.encuestas.model.Respuesta;
import com.edgz.encuestas.repository.IPreguntaRepo;
import com.edgz.encuestas.repository.IRespuestaRepo;
import com.edgz.encuestas.services.IRespuestasService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespuestasService implements IRespuestasService {

    @Autowired
    private IPreguntaRepo preguntaRepo;

    @Autowired
    private IRespuestaRepo respuestaRepo;

    @Override
    public Respuesta agregarRespuestaAPregunta(Long preguntaId, String contenido) {
        Optional<Pregunta> preguntaOptional = preguntaRepo.findById(preguntaId);
        return preguntaOptional.map(pregunta -> {
            Respuesta respuesta = new Respuesta(null, contenido, pregunta);
            pregunta.getRespuestas().add(respuesta);

            preguntaRepo.save(pregunta);
            return respuesta;
        }).orElse(null);
    }

    @Override
    public List<Respuesta> obtenerRespuestaPorPregunta(Long preguntaId) {
        return respuestaRepo.findByPreguntaId(preguntaId);
    }

    @Override
    public Optional<Respuesta> obtenerDetallesRespuesta(Long respuestaId) {
        return Optional.empty();
    }

    @Override
    public Respuesta actualizarRespuesta(Long respuestaId, String nuevoContenido) {
        return respuestaRepo.findById(respuestaId)
                .map(respuesta -> {
                    respuesta.setContenido(nuevoContenido);
                    return respuestaRepo.save(respuesta);
                }).orElse(null);
    }

    @Override
    public void eliminarRespuesta(Long respuestaId) {
        respuestaRepo.findById(respuestaId).ifPresent(respuesta -> {
            Pregunta pregunta = respuesta.getPregunta();
            pregunta.getRespuestas().remove(respuesta);

            respuestaRepo.deleteById(respuestaId);
            preguntaRepo.save(pregunta);
        });
    }
}
