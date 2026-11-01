package xyz.diego.holamundospring01.controllers;

import xyz.diego.holamundospring01.dtos.BebidaDtoIn;
import xyz.diego.holamundospring01.entities.BebidaEntidad;
import xyz.diego.holamundospring01.businessLayer.BebidasBusinessLayer;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/bebidas", "/api/v1/bebidas"})
public class BebidaController {
    private final BebidasBusinessLayer servicio;

    public BebidaController(BebidasBusinessLayer servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<BebidaEntidad> obtenerTodo() {
        return servicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public BebidaEntidad obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<BebidaEntidad> agregar(@Valid @RequestBody BebidaDtoIn datos) {
        BebidaEntidad entidad = servicio.agregar(datos);
        return ResponseEntity.created(URI.create("/api/bebidas/" + entidad.getId())).body(entidad);
    }

    @PutMapping("/{id}")
    public BebidaEntidad actualizar(@PathVariable int id, @Valid @RequestBody BebidaDtoIn datos) {
        return servicio.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        servicio.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
