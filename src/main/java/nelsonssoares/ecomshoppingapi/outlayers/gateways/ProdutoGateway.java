package nelsonssoares.ecomshoppingapi.outlayers.gateways;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.ProdutoClient;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProdutoGateway {

private final ProdutoClient produtoClient;

    public Produto findById(Integer id) {

        ResponseEntity<Produto> produtoRequest = produtoClient.findById(id);

        if (produtoRequest.getBody() == null || produtoRequest.getStatusCode().is4xxClientError() || !produtoRequest.hasBody()) {
            throw new ResponseStatusException(produtoRequest.getStatusCode(), "Produto não encontrado");
        }

        return produtoRequest.getBody();

    }
}
