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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveOrder {

    private final PedidoRepository pedidoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioClient usuarioClient;
    private final ProdutoClient produtoClient;

    @Transactional
    public Pedido executeSaveOrder(PedidoDTO pedidoDto) {
        Pedido pedido = objectMapper.convertValue(pedidoDto, Pedido.class);

        ResponseEntity<Usuario> usuario = usuarioClient.findById(pedidoDto.usuarioId());
        if(usuario.getBody() == null || !usuario.hasBody()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario não encontrado");
        }

        System.out.println(usuario);

        pedido.setUsuario(usuario.getBody());
        List<DetalhesPedido> detalhes = new ArrayList<>();
        pedidoDto.detalhesPedidoDTO().forEach(detalhe -> {

            ResponseEntity<Produto> produtoResponse = produtoClient.findById(detalhe.produtoId());
            if(produtoResponse.getBody() == null || !produtoResponse.hasBody()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado");
            }
            Produto produto = produtoResponse.getBody();
            System.out.println(produto);

            BigDecimal valorTotal = produto != null ? produto.getValorUnitario().multiply(BigDecimal.valueOf(detalhe.quantidade())) : null;
            System.out.println(valorTotal);
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
        pedido.setTotalPedido(detalhes.stream().map(DetalhesPedido::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        return pedidoRepository.save(pedido);
    }

}
