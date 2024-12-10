package com.edgz.encuestas.controllers;

import com.edgz.encuestas.model.Encuesta;
import com.edgz.encuestas.services.IEncuestaService;
import com.edgz.encuestas.services.impl.EncuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    @Autowired
    private IEncuestaService encuestaService;

    @PostMapping
    public ResponseEntity<Encuesta> crearEncuesta(@RequestBody Encuesta encuesta) throws URISyntaxException {
        Encuesta nuevaEncuesta = encuestaService.crearEncuesta(encuesta.getTitulo());
        return ResponseEntity.created(new URI("/api/encuestas" + nuevaEncuesta.getId())).body(nuevaEncuesta);
    }

    @GetMapping
    public List<Encuesta> obtenerTodasLasEncuestas(){
        return encuestaService.obtenerTodasLasEncuestas();
    }

    @GetMapping("/{encuestaId}")
    public ResponseEntity<Encuesta> obtenerDetallesEncuesta(@PathVariable Long encuestaId) {
        Optional<Encuesta> optionalEncuesta = encuestaService.obtenerDetallesEncuesta(encuestaId);
        return optionalEncuesta.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{encuestaId}")
    public ResponseEntity<Encuesta> actualizarEncuesta(@PathVariable Long encuestaId, @RequestBody Encuesta encuesta){
        Encuesta encuestaActualizada  = encuestaService.actualizarEncuesta(encuestaId, encuesta.getTitulo());
        return encuestaActualizada != null ? ResponseEntity.ok(encuestaActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{encuestaId}")
    public ResponseEntity<Void> eliminarEncuesta(@PathVariable Long encuestaId){
        encuestaService.eliminarEncuesta(encuestaId);
        return ResponseEntity.noContent().build();
    }
}
