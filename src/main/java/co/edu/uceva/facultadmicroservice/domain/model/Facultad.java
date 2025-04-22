package co.edu.uceva.facultadmicroservice.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La Facultad Debe Estar Vinculada A Un Decano")
    @Column(nullable = false)
    private Long idDecano;

    @NotBlank(message = "La Facultad Debe Tener Un Nombre")
    @Column(nullable = false)
    private String nombre;

}
