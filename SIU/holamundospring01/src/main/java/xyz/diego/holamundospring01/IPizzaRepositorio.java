package xyz.diego.holamundospring01;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPizzaRepositorio extends JpaRepository<PizzaEntidad, Integer> {
    
}
