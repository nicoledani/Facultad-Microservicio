package co.edu.uceva.facultadmicroservice.domain.service;

import co.edu.uceva.facultadmicroservice.domain.model.Facultad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFacultadService {

    Facultad save(Facultad curso);
    void delete(Facultad curso);
    Optional<Facultad> findById(Long id);
    Facultad update(Facultad curso);
    List<Facultad> findAll();
    Page<Facultad> findAll(Pageable pageable);
}
