package nelsonssoares.ecomshoppingapi.services;

import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {
    ResponseEntity<Pedido> save(PedidoDTO pedidoDto);

    ResponseEntity<Pedido> updateOrder(Integer id, Pedido pedido);

    ResponseEntity<Pedido> cancelOrder(Integer id);

    ResponseEntity<Pedido> findOrderById(Integer id);

    ResponseEntity<List<Pedido>> findOrderByUserId(Integer id);

    ResponseEntity<List<Pedido>> findOrderByStats(StatusPedido status);
}
