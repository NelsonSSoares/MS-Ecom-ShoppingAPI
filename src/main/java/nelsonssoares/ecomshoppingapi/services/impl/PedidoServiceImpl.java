package nelsonssoares.ecomshoppingapi.services.impl;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    @Override
    public ResponseEntity<Pedido> save(PedidoDTO pedidoDto) {
        return null;
    }

    @Override
    public ResponseEntity<Pedido> updateOrder(Integer id, Pedido pedido) {
        return null;
    }

    @Override
    public ResponseEntity<Pedido> cancelOrder(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Pedido> findOrderById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Pedido>> findOrderByUserId(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Pedido>> findOrderByStats(StatusPedido status) {
        return null;
    }
}
