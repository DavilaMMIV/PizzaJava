package xyz.diego.holamundospring01.repositories;

import xyz.diego.holamundospring01.entities.TamanoEntidad;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITamanoRepositorio extends JpaRepository<TamanoEntidad, Integer> {
    List<TamanoEntidad> findByActivoTrueOrderByIdAsc();
    Optional<TamanoEntidad> findByIdAndActivoTrue(int id);
}
