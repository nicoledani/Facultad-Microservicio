package co.edu.uceva.facultadmicroservice.delivery.rest;

import co.edu.uceva.facultadmicroservice.domain.exception.FacultadNoEncontradoException;
import co.edu.uceva.facultadmicroservice.domain.exception.NoHayFacultadesException;
import co.edu.uceva.facultadmicroservice.domain.exception.PaginaSinFacultadesException;
import co.edu.uceva.facultadmicroservice.domain.exception.ValidationException;
import co.edu.uceva.facultadmicroservice.domain.model.Facultad;
import co.edu.uceva.facultadmicroservice.domain.service.IFacultadService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/facultad-service")
public class FacultadRestController {

    private final IFacultadService facultadService;

    private static final String MENSAJE = "mensaje";
    private static final String CURSO = "facultad";
    private static final String CURSOS = "facultads";


    public FacultadRestController(IFacultadService facultadService) {
        this.facultadService = facultadService;
    }

    @GetMapping("/facultads")
    public ResponseEntity<Map<String, Object>> getFacultads() {
        List<Facultad> facultads = facultadService.findAll();
        if (facultads.isEmpty()) {
            throw new NoHayFacultadesException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put(CURSOS, facultads);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/facultad/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Facultad> facultads = facultadService.findAll(pageable);
        if (facultads.isEmpty()) {
            throw new PaginaSinFacultadesException(page);
        }
        return ResponseEntity.ok(facultads);
    }

    @PostMapping("/facultads")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        Map<String, Object> response = new HashMap<>();
        Facultad nuevoFacultad = facultadService.save(facultad);
        response.put(MENSAJE, "El facultad ha sido creado con éxito!");
        response.put(CURSO, nuevoFacultad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/facultads")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Facultad facultad) {
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        facultadService.delete(facultad);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El facultad ha sido eliminado con éxito!");
        response.put(CURSO, null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/facultads")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        Map<String, Object> response = new HashMap<>();
        Facultad facultadActualizado = facultadService.update(facultad);
        response.put(MENSAJE, "El facultad ha sido actualizado con éxito!");
        response.put(CURSO, facultadActualizado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/facultads/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable long id) {

        Facultad facultad = facultadService.findById(id)
                .orElseThrow(() -> new FacultadNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El facultad ha sido encontrado con éxito!");
        response.put(CURSO, facultad);
        return ResponseEntity.ok(response);
    }
}