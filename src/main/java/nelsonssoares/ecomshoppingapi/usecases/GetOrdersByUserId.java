package nelsonssoares.ecomshoppingapi.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.DetalhesPedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.ProdutoGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.UsuarioGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersByUserId {

    private final PedidoRepository pedidoRepository;
    private final UsuarioGateway usuarioGateway;
    private final ProdutoGateway produtoGateway;

    public List<PedidoResponse> executeGetOrderByUserId(Integer id) {

        List<Pedido> userOrders = pedidoRepository.findAllByUsuarioId(id);

        if (userOrders.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioGateway.findById(id);

        if(usuario == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!");
        }

        Endereco endereco = usuarioGateway.findAddressByUserId(id);

        if(endereco == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrado!");
        }

        List<PedidoResponse> pedidos = new ArrayList<>();

        for (Pedido pedido : userOrders) {


            List<DetalhesPedidoResponse> detalhesPedidoResponse = pedido.getDetalhesPedido().stream().map(detalhe -> DetalhesPedidoResponse.builder()
                    .quantidade(detalhe.getQuantidade())
                    .precoTotal(detalhe.getPrecoTotal())
                    .produto(produtoGateway.findById(detalhe.getProduto())).build()).toList();

            System.out.println(detalhesPedidoResponse);

            pedidos.add(PedidoResponse.builder()
                    .id(pedido.getId())
                    .dataCriacao(pedido.getDataCriacao())
                    .dataModificacao(pedido.getDataModificacao())
                    .statusPedido(pedido.getStatusPedido())
                    .totalPedido(pedido.getTotalPedido())
                    .detalhesPedido(detalhesPedidoResponse)
                    .usuario(usuario)
                    .endereco(endereco)
                    .build());
        }
            return pedidos;
    }


}
