package co.edu.uceva.facultadmicroservice.domain.exception;

public class FacultadExistenteException extends RuntimeException {
    public FacultadExistenteException(String nombre) {
        super("El Curso Con Nombre '" + nombre + "' Ya Existe.");
    }
}
