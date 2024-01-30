package nelsonssoares.ecomshoppingapi.domain.dtos;

import java.util.List;

public record PedidoDTO(Integer usuarioId, List<DetalhesPedidoDTO> detalhesPedidoDTO) {
}
