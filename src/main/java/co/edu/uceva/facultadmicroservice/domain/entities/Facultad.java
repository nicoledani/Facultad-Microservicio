package co.edu.uceva.facultadmicroservice.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotEmpty;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message ="No puede estar vacio")
    @Size(min=2, message="El tamaño tiene que tener minimo 2 caracteres")
    @Column(nullable=false)
    private String nombre;

    @NotNull(message = "Debe ingresar el nombre de la facultad")
    @Column(nullable = false)
    private Long idDecano;

}