package nelsonssoares.ecomshoppingapi.services.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import nelsonssoares.ecomshoppingapi.usecases.SaveOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final SaveOrder saveOrder;

    @Override
    @CircuitBreaker(name = "saveOrder", fallbackMethod = "saveFallback")
    public ResponseEntity<Pedido> save(PedidoDTO pedidoDto) {
        Pedido pedido = saveOrder.executeSaveOrder(pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
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
