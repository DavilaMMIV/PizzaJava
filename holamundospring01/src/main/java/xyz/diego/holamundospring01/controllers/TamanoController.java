package xyz.diego.holamundospring01.controllers;

import xyz.diego.holamundospring01.dtos.TamanoDtoIn;
import xyz.diego.holamundospring01.entities.TamanoEntidad;
import xyz.diego.holamundospring01.businessLayer.TamanosBusinessLayer;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/tamanos", "/api/v1/tamanos"})
public class TamanoController {
    private final TamanosBusinessLayer servicio;

    public TamanoController(TamanosBusinessLayer servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TamanoEntidad> obtenerTodo() {
        return servicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public TamanoEntidad obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<TamanoEntidad> agregar(@Valid @RequestBody TamanoDtoIn datos) {
        TamanoEntidad entidad = servicio.agregar(datos);
        return ResponseEntity.created(URI.create("/api/tamanos/" + entidad.getId())).body(entidad);
    }

    @PutMapping("/{id}")
    public TamanoEntidad actualizar(@PathVariable int id, @Valid @RequestBody TamanoDtoIn datos) {
        return servicio.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        servicio.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
