package com.edgz.encuestas.controllers;

import com.edgz.encuestas.model.Respuesta;
import com.edgz.encuestas.services.IRespuestasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/respuestas")
public class RespuestaControlller {

    @Autowired
    private IRespuestasService respuestasService;


    @PostMapping("/agregar/{preguntaId}")
    public ResponseEntity<Respuesta> agregarRespuestaPregunta(@PathVariable Long preguntaId, @RequestBody Respuesta respuesta) throws URISyntaxException {
        Respuesta nuevaRespuesta = respuestasService.agregarRespuestaAPregunta(preguntaId, respuesta.getContenido());
        return ResponseEntity.created(new URI("/api/respuestas" + nuevaRespuesta.getId())).body(nuevaRespuesta);
    }

    @GetMapping("/por-pregunta/{preguntaId}")
    public List<Respuesta> obtenerRespuestasPorPregunta(@PathVariable Long preguntaId){
        return respuestasService.obtenerRespuestaPorPregunta(preguntaId);
    }

    @GetMapping("/{repuestaId}")
    public ResponseEntity<Respuesta> obtenerDetallesDeRespuesta(@PathVariable Long respuestaId){
        Optional<Respuesta> optionalRespuesta = respuestasService.obtenerDetallesRespuesta(respuestaId);
        return optionalRespuesta.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{respuestaId}")
    public ResponseEntity<Respuesta> actualizarRespuesta(@PathVariable Long respuestId, @RequestBody Respuesta respuesta){
        Respuesta respuestaActualizada = respuestasService.actualizarRespuesta(respuestId, respuesta.getContenido());

        return respuestaActualizada != null ? ResponseEntity.ok(respuestaActualizada) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{respuestaId}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long respuestaId){
        respuestasService.eliminarRespuesta(respuestaId);
        return ResponseEntity.noContent().build();
    }
}
