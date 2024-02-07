package nelsonssoares.ecomshoppingapi.services;

import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {
    ResponseEntity<PedidoResponse> save(PedidoDTO pedidoDto);

    ResponseEntity<PedidoResponse> updateOrder(Integer id, Pedido pedido);

    ResponseEntity<PedidoResponse> cancelOrder(Integer id);

    ResponseEntity<PedidoResponse> findOrderById(Integer id);

    ResponseEntity<List<PedidoResponse>> findOrderByUserId(Integer id);

    ResponseEntity<List<PedidoResponse>> findOrderByStats(StatusPedido status);
}
