package co.edu.uceva.facultadmicroservice.domain.exception;

public class FacultadExistenteException extends RuntimeException {
    public FacultadExistenteException(String nombre)
    {
        super("La Facultad con nombre '" + nombre + "' ya existe." );
    }
}
