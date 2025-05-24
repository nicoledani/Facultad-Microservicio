package co.edu.uceva.facultadmicroservice.domain.service;

import co.edu.uceva.facultadmicroservice.domain.model.Facultad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFacultadService {

    Facultad save(Facultad facultad);
    void delete(Facultad facultad);
    Optional<Facultad> findById(Long id);
    Facultad update(Facultad facultad);
    List<Facultad> findAll();
    Page<Facultad> findAll(Pageable pageable);
}