package xyz.diego.holamundospring01.repositories;

import xyz.diego.holamundospring01.entities.ClienteEntidad;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepositorio extends JpaRepository<ClienteEntidad, Integer> {
    List<ClienteEntidad> findByActivoTrueOrderByIdAsc();
    Optional<ClienteEntidad> findByIdAndActivoTrue(int id);
}
