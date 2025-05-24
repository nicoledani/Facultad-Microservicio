package co.edu.uceva.facultadmicroservice.domain.exception;

public class PaginaSinFacultadesException extends RuntimeException {
    public PaginaSinFacultadesException(int page) {
        super("No Hay FacultadesEn La Pagina Solicitada: " + page);
    }
}