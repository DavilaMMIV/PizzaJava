package xyz.diego.holamundospring01.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pizzas")
public class PizzaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 64)
    private String nombre;

    @Column(nullable = false, length = 256)
    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private boolean activo;
}
