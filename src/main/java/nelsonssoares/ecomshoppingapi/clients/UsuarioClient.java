package nelsonssoares.ecomshoppingapi.clients;

import nelsonssoares.ecomshoppingapi.clients.entities.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient
public interface UsuarioClient {

    @GetMapping("{/id}")
    ResponseEntity<Usuario> findById(@PathVariable("id") Integer id);
}
