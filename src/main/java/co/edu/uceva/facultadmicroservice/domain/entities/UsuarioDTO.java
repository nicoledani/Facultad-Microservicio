package co.edu.uceva.facultadmicroservice.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0, message = "La cédula debe ser un número positivo")
    @NotNull(message = "La cédula no puede ser nula")
    @Column(nullable = false)
    private Long cedula;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.*\\d).+$", message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula y un número")
    @Column(nullable = false)
    @NotNull(message = "La contraseña no puede ser nula")
    private String contrasena;
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido, debe tener el formato correcto. Ejemplo: ejemplo@ejemplo.com")
    @Size(max = 50, message = "El correo no puede tener más de 100 caracteres")
    @Column(nullable = false, unique = true)
    @NotNull(message = "El correo no puede ser nulo")
    private String correo;
    @NotBlank(message = "El nombre completo no puede estar vacío")
    @Size(max = 100, message = "El nombre completo no puede tener más de 100 caracteres")
    @Column(nullable = false)
    @NotNull(message = "El nombre completo no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre completo solo puede contener letras y espacios")
    @Size(min = 3, message = "El nombre completo debe tener al menos 3 caracteres")
    private String nombreCompleto;
    @NotBlank(message = "El rol no puede estar vacío")
    @Pattern(regexp = "^(Estudiante|Docente|Decano|Administrador|Coordinador|Rector)$", message = "El rol debe ser Estudiante, Docente, Decano o Administrador, Coordinador o Rector")
    private String rol;
    @NotNull(message = "El teléfono no puede ser nulo")
    @Min(value = 100000L, message = "El teléfono debe tener al menos 6 dígitos")
    @Max(value = 9999999999L, message = "El teléfono no puede tener más de 10 dígitos")
    @Column(nullable = false)
    private Long telefono;
}