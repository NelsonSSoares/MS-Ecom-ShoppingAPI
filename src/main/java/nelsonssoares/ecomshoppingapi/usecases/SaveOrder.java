package nelsonssoares.ecomshoppingapi.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.clients.ProdutoClient;
import nelsonssoares.ecomshoppingapi.clients.UsuarioClient;
import nelsonssoares.ecomshoppingapi.clients.entities.Produto;
import nelsonssoares.ecomshoppingapi.clients.entities.Usuario;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveOrder {

    private final PedidoRepository pedidoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioClient usuarioClient;
    private final ProdutoClient produtoClient;

    public Pedido executeSaveOrder(PedidoDTO pedidoDto) {
        Pedido pedido = objectMapper.convertValue(pedidoDto, Pedido.class);

        Usuario usuario = usuarioClient.findById(pedidoDto.usuarioId()).getBody();

        System.out.println(usuario);

        pedido.setUsuario(usuario);
        List<DetalhesPedido> detalhes = new ArrayList<>();
        pedidoDto.detalhesPedidoDTO().forEach(detalhe -> {

            Produto produto = produtoClient.findById(detalhe.produtoId()).getBody();

            System.out.println(produto);

            BigDecimal valorTotal = produto != null ? produto.getValorUnitario().multiply(BigDecimal.valueOf(detalhe.quantidade())) : null;
            detalhes.add(DetalhesPedido.builder()
                    .produto(produto)
                    .quantidade(detalhe.quantidade())
                    .precoTotal(valorTotal)
                    .build());
        });
        pedido.setDetalhesPedido(detalhes);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setDataModificacao(LocalDate.now());
        pedido.setStatusPedido(StatusPedido.AGPAGAMENTO);

        return pedidoRepository.save(pedido);
    }

}
