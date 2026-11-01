package xyz.diego.holamundospring01.repositories;

import xyz.diego.holamundospring01.entities.BebidaEntidad;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBebidaRepositorio extends JpaRepository<BebidaEntidad, Integer> {
    List<BebidaEntidad> findByActivoTrueOrderByIdAsc();
    Optional<BebidaEntidad> findByIdAndActivoTrue(int id);
}
