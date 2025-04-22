package co.edu.uceva.facultadmicroservice.domain.exception;

public class FacultadNoEncontradoException extends RuntimeException {
    public FacultadNoEncontradoException(Long id) {
        super("El Curso Con ID " + id + " No Fue Encontrado.");
    }
}