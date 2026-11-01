package xyz.diego.holamundospring01.controllers;

import xyz.diego.holamundospring01.dtos.PizzaDtoIn;
import xyz.diego.holamundospring01.entities.PizzaEntidad;
import xyz.diego.holamundospring01.businessLayer.PizzasBusinessLayer;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/pizzas", "/api/v1/pizzas"})
public class PizzaController {
    private final PizzasBusinessLayer servicio;

    public PizzaController(PizzasBusinessLayer servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<PizzaEntidad> obtenerTodo() {
        return servicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public PizzaEntidad obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<PizzaEntidad> agregar(@Valid @RequestBody PizzaDtoIn datos) {
        PizzaEntidad entidad = servicio.agregar(datos);
        return ResponseEntity.created(URI.create("/api/pizzas/" + entidad.getId())).body(entidad);
    }

    @PutMapping("/{id}")
    public PizzaEntidad actualizar(@PathVariable int id, @Valid @RequestBody PizzaDtoIn datos) {
        return servicio.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        servicio.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
