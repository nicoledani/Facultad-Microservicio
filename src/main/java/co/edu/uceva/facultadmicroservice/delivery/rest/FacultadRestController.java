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
    private static final String FACULTAD = "facultad";
    private static final String FACULTADES = "facultades";


    public FacultadRestController(IFacultadService facultadService) {
        this.facultadService = facultadService;
    }

    @GetMapping("/facultades")
    public ResponseEntity<Map<String, Object>> getFacultads() {
        List<Facultad> facultades = facultadService.findAll();
        if (facultades.isEmpty()) {
            throw new NoHayFacultadesException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put(FACULTADES, facultades);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/facultad/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Facultad> facultades = facultadService.findAll(pageable);
        if (facultades.isEmpty()) {
            throw new PaginaSinFacultadesException(page);
        }
        return ResponseEntity.ok(facultades);
    }

    @PostMapping("/facultades")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        Map<String, Object> response = new HashMap<>();
        Facultad nuevoFacultad = facultadService.save(facultad);
        response.put(MENSAJE, "El facultad ha sido creado con éxito!");
        response.put(FACULTAD, nuevoFacultad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/facultades")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Facultad facultad) {
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        facultadService.delete(facultad);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El facultad ha sido eliminado con éxito!");
        response.put(FACULTAD, null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/facultades")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        Map<String, Object> response = new HashMap<>();
        Facultad facultadActualizado = facultadService.update(facultad);
        response.put(MENSAJE, "El facultad ha sido actualizado con éxito!");
        response.put(FACULTAD, facultadActualizado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/facultades/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable long id) {

        Facultad facultad = facultadService.findById(id)
                .orElseThrow(() -> new FacultadNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El facultad ha sido encontrado con éxito!");
        response.put(FACULTAD, facultad);
        return ResponseEntity.ok(response);
    }
}