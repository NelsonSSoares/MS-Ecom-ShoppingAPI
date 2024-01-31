package nelsonssoares.ecomshoppingapi.clients;

import nelsonssoares.ecomshoppingapi.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.clients.entities.Usuario;
import nelsonssoares.ecomshoppingapi.commons.constants.ClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static nelsonssoares.ecomshoppingapi.commons.constants.ClientConstants.*;

@Component
@FeignClient(name = "${feign.user-api.name}", url = "${feign.user-api.url}")
public interface UsuarioClient {

    @GetMapping(ID)
    ResponseEntity<Usuario> findById(@PathVariable("id") Integer id);

    @GetMapping(path = FINDADDRESSBYUSER_ID)
    ResponseEntity<List<Endereco>> findAddressByUserId(@PathVariable("id") Integer id);
}
