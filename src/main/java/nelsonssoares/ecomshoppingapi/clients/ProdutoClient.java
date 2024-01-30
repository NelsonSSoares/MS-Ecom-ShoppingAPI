package nelsonssoares.ecomshoppingapi.clients;

import nelsonssoares.ecomshoppingapi.clients.entities.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "${feign.product-api.name}", url = "${feign.product-api.url}")
public interface ProdutoClient {
    @GetMapping("/{id}")
    ResponseEntity<Produto> findById(@PathVariable("id") Integer id);
}
