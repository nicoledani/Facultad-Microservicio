package co.edu.uceva.facultadmicroservice.domain.repository;

import co.edu.uceva.facultadmicroservice.domain.entities.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacultadRepository extends JpaRepository<Facultad, Long> {
}