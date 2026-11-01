package xyz.diego.holamundospring01.businessLayer;

import xyz.diego.holamundospring01.dtos.BebidaDtoIn;
import xyz.diego.holamundospring01.entities.BebidaEntidad;
import xyz.diego.holamundospring01.repositories.IBebidaRepositorio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class BebidasBusinessLayer {
    private final IBebidaRepositorio repositorio;

    public BebidasBusinessLayer(IBebidaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<BebidaEntidad> obtenerTodo() {
        return repositorio.findByActivoTrueOrderByIdAsc();
    }

    public BebidaEntidad obtenerPorId(int id) {
        return repositorio.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    @Transactional
    public BebidaEntidad agregar(BebidaDtoIn datos) {
        BebidaEntidad entidad = new BebidaEntidad();
        copiar(datos, entidad);
        entidad.setActivo(true);
        return repositorio.save(entidad);
    }

    @Transactional
    public BebidaEntidad actualizar(int id, BebidaDtoIn datos) {
        BebidaEntidad entidad = obtenerPorId(id);
        copiar(datos, entidad);
        return repositorio.save(entidad);
    }

    @Transactional
    public void borrar(int id) {
        BebidaEntidad entidad = obtenerPorId(id);
        entidad.setActivo(false);
        repositorio.save(entidad);
    }

    private void copiar(BebidaDtoIn datos, BebidaEntidad entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setDescripcion(datos.descripcion());
        entidad.setPrecio(datos.precio());
    }
}
