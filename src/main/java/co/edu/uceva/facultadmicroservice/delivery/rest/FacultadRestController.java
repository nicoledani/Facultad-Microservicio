package co.edu.uceva.facultadmicroservice.delivery.rest;

import co.edu.uceva.facultadmicroservice.domain.entities.Facultad;
import co.edu.uceva.facultadmicroservice.domain.entities.UsuarioDTO;
import co.edu.uceva.facultadmicroservice.domain.exception.*;
import co.edu.uceva.facultadmicroservice.domain.service.IFacultadService;
import co.edu.uceva.facultadmicroservice.domain.service.IUsuarioClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/Facultad-Microservice")
public class FacultadRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final IFacultadService facultadService;
    private final IUsuarioClient usuarioClient;

    private static final String MENSAJE = "mensaje";
    private static final String FACULTAD = "facultad";
    private static final String FACULTADES = "facultades";
    private static final String USUARIOS = "usuarios";


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

    @GetMapping("/decanos")
    public ResponseEntity<Map<String, Object>> getDocentes() {
        ObjectMapper mapper = new ObjectMapper();
        //https://stackoverflow.com/questions/28821715/java-lang-classcastexception-java-util-linkedhashmap-cannot-be-cast-to-com-test
        List<UsuarioDTO> usuarios = mapper.convertValue(usuarioClient.getUsuarios().getBody().get(USUARIOS), new TypeReference<List<UsuarioDTO>>(){});
        List<UsuarioDTO> decanos = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for(UsuarioDTO usuario : usuarios) {
            if(usuario.getRol().equals("Decano")) {
                decanos.add(usuario);
            }
        }
        if (decanos.isEmpty()) {
            throw new NoHayDecanosException();
        }
        response.put(USUARIOS, decanos);
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
        Map<String, Object> response = new HashMap<>();
        Facultad nuevaFacultad = facultadService.save(facultad);
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
     * @param facultad: Objeto Facultad que se va a actualizar
     */
    @PutMapping("/facultades")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Facultad facultad, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
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

}


