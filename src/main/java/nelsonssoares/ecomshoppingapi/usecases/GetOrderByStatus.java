package nelsonssoares.ecomshoppingapi.usecases;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.DetalhesPedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.ProdutoGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.UsuarioGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderByStatus {

    private final PedidoRepository pedidoRepository;
    private final ProdutoGateway produtoGateway;
    private final UsuarioGateway usuarioGateway;

    public List<PedidoResponse> executeGetOrderByStatus(StatusPedido statusPedido){

        List<Pedido> pedidos = pedidoRepository.findAllByStatusPedido(statusPedido);

        if(pedidos == null){
            return null;
        }

        List<PedidoResponse> pedidosResponse = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            Usuario usuario = usuarioGateway.findById(pedido.getUsuarioId());
            Endereco endereco = usuarioGateway.findAddressByUserId(pedido.getUsuarioId());

            List<DetalhesPedidoResponse> detalhesPedidoResponse = new ArrayList<>();
            for (DetalhesPedido detalhe : pedido.getDetalhesPedido()) {
                Produto produto = produtoGateway.findById(detalhe.getProduto());
                detalhesPedidoResponse.add(DetalhesPedidoResponse.builder()
                        .quantidade(detalhe.getQuantidade())
                        .produto(produto)
                        .precoTotal(detalhe.getPrecoTotal())
                        .build());
            }

            pedidosResponse.add(PedidoResponse.builder()
                    .id(pedido.getId())
                    .dataCriacao(pedido.getDataCriacao())
                    .dataModificacao(pedido.getDataModificacao())
                    .statusPedido(pedido.getStatusPedido())
                    .usuario(usuario)
                    .endereco(endereco)
                    .detalhesPedido(detalhesPedidoResponse)
                    .totalPedido(pedido.getTotalPedido())
                    .build());

        }

        return pedidosResponse;
    }
}
