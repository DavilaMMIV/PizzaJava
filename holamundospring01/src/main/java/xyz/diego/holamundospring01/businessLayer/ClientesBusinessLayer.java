package xyz.diego.holamundospring01.businessLayer;

import xyz.diego.holamundospring01.dtos.ClienteDtoIn;
import xyz.diego.holamundospring01.entities.ClienteEntidad;
import xyz.diego.holamundospring01.repositories.IClienteRepositorio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class ClientesBusinessLayer {
    private final IClienteRepositorio repositorio;

    public ClientesBusinessLayer(IClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<ClienteEntidad> obtenerTodo() {
        return repositorio.findByActivoTrueOrderByIdAsc();
    }

    public ClienteEntidad obtenerPorId(int id) {
        return repositorio.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    @Transactional
    public ClienteEntidad agregar(ClienteDtoIn datos) {
        ClienteEntidad entidad = new ClienteEntidad();
        copiar(datos, entidad);
        entidad.setActivo(true);
        return repositorio.save(entidad);
    }

    @Transactional
    public ClienteEntidad actualizar(int id, ClienteDtoIn datos) {
        ClienteEntidad entidad = obtenerPorId(id);
        copiar(datos, entidad);
        return repositorio.save(entidad);
    }

    @Transactional
    public void borrar(int id) {
        ClienteEntidad entidad = obtenerPorId(id);
        entidad.setActivo(false);
        repositorio.save(entidad);
    }

    private void copiar(ClienteDtoIn datos, ClienteEntidad entidad) {
        entidad.setNombre(datos.nombre());
        entidad.setTelefono(datos.telefono());
        entidad.setCorreo(datos.correo());
        entidad.setDireccion(datos.direccion());
    }
}
