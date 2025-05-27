package co.edu.uceva.facultadmicroservice.delivery.rest;

import co.edu.uceva.facultadmicroservice.domain.entities.Facultad;
import co.edu.uceva.facultadmicroservice.domain.entities.UsuarioDTO;
import co.edu.uceva.facultadmicroservice.domain.exception.*;
import co.edu.uceva.facultadmicroservice.domain.service.IFacultadService;
import co.edu.uceva.facultadmicroservice.domain.service.IUsuarioClient;
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

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/Facultad-Microservice")
public class FacultadRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final IFacultadService facultadService;
    private final IUsuarioClient usuarioClient;

    private static final String MENSAJE = "mensaje";
    private static final String FACULTAD = "facultad";
    private static final String FACULTADES = "facultades";


    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    public FacultadRestController(IFacultadService facultadService, IUsuarioClient usuarioClient) {
        this.facultadService = facultadService;
        this.usuarioClient = usuarioClient;
    }

    /**
     * Listar todos las facultades.
     */
    @GetMapping("/facultades")
    public ResponseEntity<Map<String, Object>> getFacultades() {
        List<Facultad> facultades = facultadService.findAll();
        if (facultades.isEmpty()) {
            throw new NoHayFacultadesException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put(FACULTADES, facultades);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar facultades con paginación.
     */
    @GetMapping("/facultad/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Facultad> facultades = facultadService.findAll(pageable);
        if (facultades.isEmpty()) {
            throw new PaginaSinFacultadesException(page);
        }
        return ResponseEntity.ok(facultades);
    }

    /**
     * Crear una nueva facultad pasando el objeto en el cuerpo de la petición.
     */
    @PostMapping("/facultades")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        comprobarDecanos(facultad.getId_decano());
        Facultad nuevaFacultad = facultadService.save(facultad);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "La facultad ha sido creado con éxito!");
        response.put(FACULTAD, nuevaFacultad);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Eliminar una facultad pasando el objeto en el cuerpo de la petición.
     */
    @DeleteMapping("/facultades")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Facultad facultad) {
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        facultadService.delete(facultad);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "La facultad ha sido eliminada con éxito!");
        response.put(FACULTAD, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar una facultad pasando el objeto en el cuerpo de la petición.
     *
     * @param facultad: Objeto Facultad que se va a actualizar
     */
    @PutMapping("/facultades")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        comprobarDecanos(facultad.getId_decano());
        facultadService.findById(facultad.getId())
                .orElseThrow(() -> new FacultadNoEncontradoException(facultad.getId()));
        Map<String, Object> response = new HashMap<>();
        Facultad facultadActualizada = facultadService.update(facultad);
        response.put(MENSAJE, "La facultad ha sido actualizado con éxito!");
        response.put(FACULTAD, facultadActualizada);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener un facultad por su ID.
     */
    @GetMapping("/facultades/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Facultad facultad = facultadService.findById(id)
                .orElseThrow(() -> new FacultadNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "La facultad ha sido encontrada con éxito!");
        response.put(FACULTAD, facultad);
        return ResponseEntity.ok(response);
    }

    /**
     * Comprobar si el decano existe en el sistema.
     */
    private void comprobarDecanos(Long idDecano) {
        Map<String, List<UsuarioDTO>> response = usuarioClient.idusuario();
        List<UsuarioDTO> decanos = response.get("usuarios");
        boolean existe = decanos.stream()
                .anyMatch(d -> d.getId().equals(idDecano));
        if (!existe) {
            throw new RuntimeException(("El decano con id: " + idDecano + " no existe"));
        }
    }
}

