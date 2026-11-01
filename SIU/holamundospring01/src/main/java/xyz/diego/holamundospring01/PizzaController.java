package xyz.diego.holamundospring01;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@RestController 
public class PizzaController {

    @Autowired
    IPizzaRepositorio repositorio;

    @GetMapping("/api/pizzas")
    public List<PizzaEntidad> obtenerTodo(){
        List<PizzaEntidad> pizzas = repositorio.findAll();

        return pizzas;
    }

    @PostMapping("/api/pizzas")
    public int agreagr (PizzaEntidad entidad) {
        PizzaEntidad pizzaEntidad = repositorio.save(entidad);
        
        return pizzaEntidad.getId();
    }
    
}
