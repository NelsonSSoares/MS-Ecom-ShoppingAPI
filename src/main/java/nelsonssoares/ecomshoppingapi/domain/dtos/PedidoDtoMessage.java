package nelsonssoares.ecomshoppingapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class PedidoDtoMessage implements Serializable {
    private Integer usuarioId;
    private List<DetalhesPedidoDTO> detalhesPedidoDTO;
}
