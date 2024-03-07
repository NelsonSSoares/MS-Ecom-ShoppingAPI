package nelsonssoares.ecomshoppingapi.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.commons.infra.PedidoPublisher;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import nelsonssoares.ecomshoppingapi.usecases.*;
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
    private final GetOrderByStatus getOrderByStatus;
    private final UpdateOrder updateOrder;
    private final PatchOrderStatus patchOrderStatus;
    private final PedidoPublisher pedidoPublisher;


    @Override
    @CircuitBreaker(name = "saveOrderCB", fallbackMethod = "saveFallback")
    public ResponseEntity<PedidoResponse> save(PedidoDTO pedidoDto) {
        PedidoResponse pedido = saveOrder.executeSaveOrder(pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @Override
    public ResponseEntity<PedidoResponse> updateOrder(Integer id, PedidoDTO pedido) {
        PedidoResponse pedidoResponse = updateOrder.executeUpdateOrder(id, pedido);
        return pedidoResponse != null ? ResponseEntity.status(HttpStatus.OK).body(pedidoResponse) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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


        List<PedidoResponse> pedidos = getOrderByStatus.executeGetOrderByStatus(status);

        return pedidos != null ? ResponseEntity.status(HttpStatus.OK).body(pedidos) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<Pedido> pathOrderStatus(Integer id, Pedido status) {
        Pedido pedido = patchOrderStatus.executePathOrderStatus(id, status);
        return pedido != null ? ResponseEntity.status(HttpStatus.OK).body(pedido) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }



    public ResponseEntity<PedidoDTO> saveFallback(PedidoDTO pedidoDto, Throwable throwable) throws JsonProcessingException {

        if (pedidoPublisher.sendPedido(pedidoDto)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(pedidoDto);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(pedidoDto);
    }
}