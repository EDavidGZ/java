package com.edgz.encuestas.controllers;

import com.edgz.encuestas.model.Pregunta;
import com.edgz.encuestas.services.IPreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pregunta")
public class PreguntaController {

    @Autowired
    private IPreguntaService preguntaService;

    @PostMapping("agregar/{encuestaId}")
    public ResponseEntity<Pregunta> agregarPreguntaEncuesta(@PathVariable Long encuestaId, @RequestBody Pregunta pregunta) throws URISyntaxException {
            Pregunta nuevaPregunta = preguntaService.agregarPreguntaAEncuesta(encuestaId, pregunta.getContenido());
            return ResponseEntity.created(new URI("/api/pregunta" + nuevaPregunta.getId())).body(nuevaPregunta);

    }

    @GetMapping("/por-encuesta/{encuestaId}")
    public List<Pregunta> obtenerPreguntasPorEncuesta(@PathVariable Long encuestaId){
            return preguntaService.obtenerPreguntaPorEncuesta(encuestaId);
    }

    @GetMapping("/{preguntaId}")
    public ResponseEntity<Pregunta> obtenerDetallesPregunta(@PathVariable Long preguntaId) {
        return preguntaService.obtenerDetallesPregunta(preguntaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{preguntaId}/encuesta/{encuestaId}")
    public ResponseEntity<Pregunta> actualizarPregunta(@PathVariable Long preguntaId, @RequestBody Pregunta pregunta, @PathVariable Long encuestaId){
        Pregunta preguntaActualizada = preguntaService.actualizarPregunta(preguntaId, pregunta.getContenido(), encuestaId);

        return preguntaActualizada != null ? ResponseEntity.ok(preguntaActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{preguntaId}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Long preguntaId){
        preguntaService.eliminarPregunta((preguntaId));
        return ResponseEntity.noContent().build();
    }
}
