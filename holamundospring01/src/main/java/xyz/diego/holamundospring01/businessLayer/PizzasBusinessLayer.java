package xyz.diego.holamundospring01.businessLayer;

import xyz.diego.holamundospring01.dtos.PizzaDtoIn;
import xyz.diego.holamundospring01.entities.PizzaEntidad;
import xyz.diego.holamundospring01.repositories.IPizzaRepositorio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class PizzasBusinessLayer {
    private final IPizzaRepositorio repositorio;

    public PizzasBusinessLayer(IPizzaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<PizzaEntidad> obtenerTodo() {
        return repositorio.findByActivoTrueOrderByIdAsc();
    }

    public PizzaEntidad obtenerPorId(int id) {
        return repositorio.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    @Transactional
    public PizzaEntidad agregar(PizzaDtoIn datos) {
        PizzaEntidad entidad = new PizzaEntidad();
        copiar(datos, entidad);
        entidad.setActivo(true);
        return repositorio.save(entidad);
    }

    @Transactional
    public PizzaEntidad actualizar(int id, PizzaDtoIn datos) {
        PizzaEntidad entidad = obtenerPorId(id);
        copiar(datos, entidad);
        return repositorio.save(entidad);
    }

    @Transactional
    public void borrar(int id) {
        PizzaEntidad entidad = obtenerPorId(id);
        entidad.setActivo(false);
        repositorio.save(entidad);
    }

    private void copiar(PizzaDtoIn datos, PizzaEntidad entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setDescripcion(datos.descripcion());
        entidad.setPrecio(datos.precio());
    }
}
