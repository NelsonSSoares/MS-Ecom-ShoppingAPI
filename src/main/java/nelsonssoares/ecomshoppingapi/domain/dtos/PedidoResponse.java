package nelsonssoares.ecomshoppingapi.domain.dtos;

import lombok.Builder;
import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record PedidoResponse(Integer id, Usuario usuario, LocalDate dataCriacao, LocalDate dataModificacao, StatusPedido statusPedido, BigDecimal totalPedido, List<DetalhesPedidoResponse> detalhesPedido, Endereco endereco) {
}
