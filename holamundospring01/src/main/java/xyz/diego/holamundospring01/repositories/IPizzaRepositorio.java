package xyz.diego.holamundospring01.repositories;

import xyz.diego.holamundospring01.entities.PizzaEntidad;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPizzaRepositorio extends JpaRepository<PizzaEntidad, Integer> {
    List<PizzaEntidad> findByActivoTrueOrderByIdAsc();
    Optional<PizzaEntidad> findByIdAndActivoTrue(int id);
}
