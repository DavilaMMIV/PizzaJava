package xyz.diego.holamundospring01.businessLayer;

import xyz.diego.holamundospring01.dtos.TamanoDtoIn;
import xyz.diego.holamundospring01.entities.TamanoEntidad;
import xyz.diego.holamundospring01.repositories.ITamanoRepositorio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class TamanosBusinessLayer {
    private final ITamanoRepositorio repositorio;

    public TamanosBusinessLayer(ITamanoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<TamanoEntidad> obtenerTodo() {
        return repositorio.findByActivoTrueOrderByIdAsc();
    }

    public TamanoEntidad obtenerPorId(int id) {
        return repositorio.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    @Transactional
    public TamanoEntidad agregar(TamanoDtoIn datos) {
        TamanoEntidad entidad = new TamanoEntidad();
        copiar(datos, entidad);
        entidad.setActivo(true);
        return repositorio.save(entidad);
    }

    @Transactional
    public TamanoEntidad actualizar(int id, TamanoDtoIn datos) {
        TamanoEntidad entidad = obtenerPorId(id);
        copiar(datos, entidad);
        return repositorio.save(entidad);
    }

    @Transactional
    public void borrar(int id) {
        TamanoEntidad entidad = obtenerPorId(id);
        entidad.setActivo(false);
        repositorio.save(entidad);
    }

    private void copiar(TamanoDtoIn datos, TamanoEntidad entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setDescripcion(datos.descripcion());
        entidad.setPrecio(datos.precio());
    }
}
