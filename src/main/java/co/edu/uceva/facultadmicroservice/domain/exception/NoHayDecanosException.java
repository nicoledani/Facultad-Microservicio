package co.edu.uceva.facultadmicroservice.domain.exception;

public class NoHayDecanosException extends RuntimeException {
  public NoHayDecanosException() {
    super("No hay decanos en la base de datos.");
  }
}