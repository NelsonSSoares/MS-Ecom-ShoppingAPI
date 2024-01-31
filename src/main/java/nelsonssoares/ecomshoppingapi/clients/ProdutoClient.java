package nelsonssoares.ecomshoppingapi.clients;

import nelsonssoares.ecomshoppingapi.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.commons.constants.ClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static nelsonssoares.ecomshoppingapi.commons.constants.ClientConstants.ID;

@Component
@FeignClient(name = "${feign.product-api.name}", url = "${feign.product-api.url}")
public interface ProdutoClient {
    @GetMapping(ID)
    ResponseEntity<Produto> findById(@PathVariable("id") Integer id);
}
