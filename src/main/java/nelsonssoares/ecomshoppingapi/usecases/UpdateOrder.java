package nelsonssoares.ecomshoppingapi.usecases;


import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.DetalhesPedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.DetalhesPedidoRepository;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.ProdutoGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.UsuarioGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateOrder {

    private final PedidoRepository pedidoRepository;
    private final DetalhesPedidoRepository detalhesPedidoRepository;
    private final ProdutoGateway produtoGateway;
    private final UsuarioGateway usuarioGateway;

    @Transactional
    public PedidoResponse executeUpdateOrder(Integer id, PedidoDTO pedidoDto) {

        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            return null;
        }
        System.out.println("pedido: "+pedido);

        Endereco endereco = usuarioGateway.findAddressByUserId(pedidoDto.usuarioId());
        if( endereco == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }

        Usuario usuario = usuarioGateway.findById(pedidoDto.usuarioId());
        if( usuario == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        List<DetalhesPedido> detalhes = new ArrayList<>();
        List<DetalhesPedidoResponse> detalhesPedidoResponse = new ArrayList<>();

        pedidoDto.detalhesPedidoDTO().forEach(detalhe -> {

            Produto produto = produtoGateway.findById(detalhe.produtoId());


            detalhesPedidoResponse.add(DetalhesPedidoResponse.builder()
                    .produto(produto)
                    .quantidade(detalhe.quantidade())
                    .precoTotal(produto.getValorUnitario().multiply(BigDecimal.valueOf(detalhe.quantidade())))
                    .build());

            BigDecimal valorTotal = produto != null ? produto.getValorUnitario().multiply(BigDecimal.valueOf(detalhe.quantidade())) : null;

            detalhes.add(DetalhesPedido.builder()
                    .produto(produto.getId())
                    .quantidade(detalhe.quantidade())
                    .precoTotal(valorTotal)
                    .build());
        });

        pedido.setId(id);
        pedido.setDetalhesPedido(detalhes);
        pedido.setEnderecoId(endereco.getId());
        pedido.setUsuarioId(usuario.getId());
        pedido.setDataModificacao(LocalDate.now());
        pedido.setStatusPedido(pedido.getStatusPedido());
        pedido.setTotalPedido(detalhes.stream().map(DetalhesPedido::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        detalhesPedidoRepository.saveAll(detalhes);
        pedidoRepository.save(pedido);

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
