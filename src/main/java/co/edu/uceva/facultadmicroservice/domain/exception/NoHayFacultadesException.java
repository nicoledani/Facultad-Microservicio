package co.edu.uceva.facultadmicroservice.domain.exception;

public class NoHayFacultadesException extends RuntimeException {
    public NoHayFacultadesException() {
        super("No Hay Productos En La Base De Datos.");
    }
}