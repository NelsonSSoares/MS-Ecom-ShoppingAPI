package nelsonssoares.ecomshoppingapi.services.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import nelsonssoares.ecomshoppingapi.usecases.GetOrderById;
import nelsonssoares.ecomshoppingapi.usecases.GetOrdersByUserId;
import nelsonssoares.ecomshoppingapi.usecases.SaveOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final SaveOrder saveOrder;
    private final GetOrderById getOrderById;
    private final GetOrdersByUserId getOrdersByUserId;

    @Override
    @CircuitBreaker(name = "saveOrder", fallbackMethod = "saveFallback")
    public ResponseEntity<PedidoResponse> save(PedidoDTO pedidoDto) {
        PedidoResponse pedido = saveOrder.executeSaveOrder(pedidoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @Override
    public ResponseEntity<PedidoResponse> updateOrder(Integer id, Pedido pedido) {
        return null;
    }

    @Override
    public ResponseEntity<PedidoResponse> cancelOrder(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PedidoResponse> findOrderById(Integer id) {

        PedidoResponse pedido = getOrderById.executeGetOrderById(id);

        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @Override
    public ResponseEntity<List<PedidoResponse>> findOrderByUserId(Integer id) {

        List<PedidoResponse> pedidos = getOrdersByUserId.executeGetOrderByUserId(id);

        return pedidos != null ? ResponseEntity.status(HttpStatus.OK).body(pedidos) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<List<PedidoResponse>> findOrderByStats(StatusPedido status) {
        return null;
    }

    public ResponseEntity<PedidoResponse> saveFallback(PedidoDTO pedidoDto, Throwable throwable) {
        System.out.println("Circuit Breaker saveOrder has been opened" + throwable.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
