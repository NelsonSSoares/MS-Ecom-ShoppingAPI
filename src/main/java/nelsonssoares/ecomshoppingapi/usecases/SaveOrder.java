package nelsonssoares.ecomshoppingapi.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.DetalhesPedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.DetalhesPedidoRepository;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.ProdutoGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.UsuarioGateway;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveOrder {

    private final PedidoRepository pedidoRepository;
    private final DetalhesPedidoRepository detalhesPedidoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioGateway usuarioGateway;
    private final ProdutoGateway produtoGateway;

    @Transactional
    public PedidoResponse executeSaveOrder(PedidoDTO pedidoDto) {


        Pedido pedido = objectMapper.convertValue(pedidoDto, Pedido.class);

        Usuario usuario = usuarioGateway.findById(pedidoDto.usuarioId());

        Endereco endereco = usuarioGateway.findAddressByUserId(pedidoDto.usuarioId());

        pedido.setEnderecoId(endereco.getId());
        pedido.setUsuarioId(usuario.getId());

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
            System.out.println(valorTotal);
            detalhes.add(DetalhesPedido.builder()
                    .produto(produto.getId())
                    .quantidade(detalhe.quantidade())
                    .precoTotal(valorTotal)
                    .build());
        });

        pedido.setDetalhesPedido(detalhes);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setDataModificacao(LocalDate.now());
        pedido.setStatusPedido(StatusPedido.AGPAGAMENTO);
        pedido.setTotalPedido(detalhes.stream().map(DetalhesPedido::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        detalhesPedidoRepository.saveAll(detalhes);
        pedidoRepository.save(pedido);

        return PedidoResponse.builder()
                .id(pedido.getId())
                .dataCriacao(pedido.getDataCriacao())
                .dataModificacao(pedido.getDataModificacao())
                .statusPedido(pedido.getStatusPedido())
                .totalPedido(pedido.getTotalPedido())
                .endereco(endereco)
                .usuario(usuario)
                .detalhesPedidoResponse(detalhesPedidoResponse)
                .build();
    }

}
