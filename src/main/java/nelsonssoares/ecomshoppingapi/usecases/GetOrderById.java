package nelsonssoares.ecomshoppingapi.usecases;


import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.DetalhesPedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.ProdutoGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.UsuarioGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderById {

    private final PedidoRepository pedidoRepository;
    private final UsuarioGateway usuarioGateway;
    private final ProdutoGateway produtoGateway;


    public PedidoResponse executeGetOrderById(Integer id) {

        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null || pedido.getStatusPedido().equals(StatusPedido.CANCELADO)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        Usuario usuario = usuarioGateway.findById(pedido.getUsuarioId());

        if( usuario == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        Endereco endereco = usuarioGateway.findAddressByUserId(pedido.getUsuarioId());

        if( endereco == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }


        List<DetalhesPedidoResponse> detalhesPedidoResponse = pedido.getDetalhesPedido().stream().map(detalhe -> DetalhesPedidoResponse.builder()
                .quantidade(detalhe.getQuantidade())
                .precoTotal( detalhe.getPrecoTotal())
       .produto(produtoGateway.findById(detalhe.getProduto())).build()).toList();

        return PedidoResponse.builder()
                .id(pedido.getId())
                .dataCriacao(pedido.getDataCriacao())
                .dataModificacao(pedido.getDataModificacao())
                .statusPedido(pedido.getStatusPedido())
                .totalPedido(pedido.getTotalPedido())
                .detalhesPedido(detalhesPedidoResponse)
                .usuario(usuario)
                .endereco(endereco)
                .build();
    }
}
