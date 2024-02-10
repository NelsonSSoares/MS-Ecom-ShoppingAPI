package nelsonssoares.ecomshoppingapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@AllArgsConstructor
@Data
public class DetalhesPedidoDtoMessage implements Serializable {

    private Integer produtoId;
    private Integer quantidade;
}
