package com.edgz.encuestas.services.impl;

import com.edgz.encuestas.model.Encuesta;
import com.edgz.encuestas.model.Pregunta;
import com.edgz.encuestas.repository.IEncuestaRepo;
import com.edgz.encuestas.repository.IPreguntaRepo;
import com.edgz.encuestas.services.IPreguntaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PreguntaService implements IPreguntaService {

    @Autowired
    private IPreguntaRepo preguntaRepo;

    @Autowired
    private IEncuestaRepo encuestaRepo;


    @Override
    public Pregunta agregarPreguntaAEncuesta(Long encuestaId, String contenido) {
       try {
           Optional<Encuesta> optionalEncuesta = encuestaRepo.findById(encuestaId);
        return optionalEncuesta.map(encuesta -> {
            Pregunta pregunta = new Pregunta(null, contenido, encuesta, new ArrayList<>());
            encuestaRepo.save(encuesta);
            return pregunta;
        }).orElse(null);
    } catch (Exception e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no se puedo agregar la pregunta");
    }
    }

    @Override
    public List<Pregunta> obtenerPreguntaPorEncuesta(Long encuestaId) {
        return preguntaRepo.findByEncuestaId(encuestaId);
    }

    @Override
    public Optional<Pregunta> obtenerDetallesPregunta(Long preguntaId) {
        return preguntaRepo.findById(preguntaId);
    }

    @Override
    public Pregunta actualizarPregunta(Long preguntaId, String nuevoContenido, Long encuestaId) {
        return preguntaRepo.findById(preguntaId)
                .map(pregunta -> {
                    pregunta.setContenido(nuevoContenido);
                    Optional<Encuesta> optionalEncuesta = encuestaRepo.findById(encuestaId);
                    optionalEncuesta.ifPresent(pregunta::setEncuesta);
                    return preguntaRepo.save(pregunta);
                }).orElse(null);
    }

    @Override
    public void eliminarPregunta(Long preguntaId) {
        preguntaRepo.findById(preguntaId).ifPresent(pregunta -> {
            Encuesta encuesta = pregunta.getEncuesta();
            encuesta.getPreguntas().remove(pregunta);

            preguntaRepo.deleteById(preguntaId);
            encuestaRepo.save(encuesta);
        });

    }
}
