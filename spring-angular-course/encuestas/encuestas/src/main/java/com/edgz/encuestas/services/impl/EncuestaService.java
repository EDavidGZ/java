package com.edgz.encuestas.services.impl;

import com.edgz.encuestas.model.Encuesta;
import com.edgz.encuestas.repository.IEncuestaRepo;
import com.edgz.encuestas.services.IEncuestaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EncuestaService implements IEncuestaService {

    @Autowired
    private IEncuestaRepo encuestaRepo;


    @Override
    public Encuesta crearEncuesta(String titulo) {
        Encuesta encuesta = new Encuesta();
        encuesta.setTitulo(titulo);
        return encuestaRepo.save(encuesta);
    }

    @Override
    public List<Encuesta> obtenerTodasLasEncuestas() {
        return encuestaRepo.findAll();
    }

    @Override
    public Optional<Encuesta> obtenerDetallesEncuesta(Long encuestaId) {
        return encuestaRepo.findById(encuestaId);
    }

    @Override
    public Encuesta actualizarEncuesta(Long encuestaId, String nuevoTitulo) {
        return encuestaRepo.findById(encuestaId)
                .map(encuesta -> {
                    encuesta.setTitulo(nuevoTitulo);
                    return encuestaRepo.save(encuesta);
                })
                .orElse(null);
    }

    @Override
    public void eliminarEncuesta(Long encuestaId) {
        encuestaRepo.deleteById(encuestaId);
    }
}
