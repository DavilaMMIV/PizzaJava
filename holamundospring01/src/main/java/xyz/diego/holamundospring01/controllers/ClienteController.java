package xyz.diego.holamundospring01.controllers;

import xyz.diego.holamundospring01.dtos.ClienteDtoIn;
import xyz.diego.holamundospring01.entities.ClienteEntidad;
import xyz.diego.holamundospring01.businessLayer.ClientesBusinessLayer;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/clientes", "/api/v1/clientes"})
public class ClienteController {
    private final ClientesBusinessLayer servicio;

    public ClienteController(ClientesBusinessLayer servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<ClienteEntidad> obtenerTodo() {
        return servicio.obtenerTodo();
    }

    @GetMapping("/{id}")
    public ClienteEntidad obtenerPorId(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ClienteEntidad> agregar(@Valid @RequestBody ClienteDtoIn datos) {
        ClienteEntidad entidad = servicio.agregar(datos);
        return ResponseEntity.created(URI.create("/api/clientes/" + entidad.getId())).body(entidad);
    }

    @PutMapping("/{id}")
    public ClienteEntidad actualizar(@PathVariable int id, @Valid @RequestBody ClienteDtoIn datos) {
        return servicio.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        servicio.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
