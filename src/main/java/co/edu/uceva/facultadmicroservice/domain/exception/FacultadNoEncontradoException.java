package co.edu.uceva.facultadmicroservice.domain.exception;

public class FacultadNoEncontradoException extends RuntimeException {
    public FacultadNoEncontradoException(Long id) {
        super("La facultad con el ID" + id + " no fue encontrada.");
    }
}