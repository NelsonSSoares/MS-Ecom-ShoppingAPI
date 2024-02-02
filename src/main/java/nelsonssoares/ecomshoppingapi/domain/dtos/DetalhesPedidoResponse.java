package nelsonssoares.ecomshoppingapi.domain.dtos;

import lombok.Builder;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Produto;

import java.math.BigDecimal;

@Builder
//TALVEZ SEJA POSSIVEL EXCLUIR ESTE RECORD
public record DetalhesPedidoResponse(Produto produto, Integer quantidade, BigDecimal precoTotal) {

}
