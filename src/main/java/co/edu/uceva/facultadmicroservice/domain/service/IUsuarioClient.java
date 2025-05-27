package co.edu.uceva.facultadmicroservice.domain.service;

import co.edu.uceva.facultadmicroservice.domain.entities.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "usuario-service")
public interface IUsuarioClient {
    @GetMapping("api/v1/usuario-service/usuarios/decanos")
    Map<String, List<UsuarioDTO>> idusuario();
}