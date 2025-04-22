package co.edu.uceva.facultadmicroservice.domain.exception;

public class PaginaSinFacultadesException extends RuntimeException {
    public PaginaSinFacultadesException(int page) {
        super("No Hay Cursos En La Pagina Solicitada: " + page);
    }
}