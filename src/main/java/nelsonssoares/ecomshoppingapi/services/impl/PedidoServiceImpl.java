package nelsonssoares.ecomshoppingapi.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import nelsonssoares.ecomshoppingapi.usecases.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nelsonssoares.ecomshoppingapi.commons.configs.RabbitMQConfig.QUEUE_NAME;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final SaveOrder saveOrder;
    private final GetOrderById getOrderById;
    private final GetOrdersByUserId getOrdersByUserId;
    private final GetOrderByStatus getOrderByStatus;
    private final UpdateOrder updateOrder;
    private final PatchOrderStatus patchOrderStatus;
    private final RabbitTemplate rabbitTemplate;
    //private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;


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

    private final Map<Integer,List<PedidoDTO>> CACHE = new HashMap<>();

    private ResponseEntity<PedidoDTO> saveFallback(PedidoDTO pedidoDto, Throwable throwable) throws JsonProcessingException {
        System.out.println("Fallback method called");
        System.out.println(throwable);
        System.out.println("Enviando mensagem para fila de pedidos");
        System.out.println(pedidoDto);
        //Message message = new Message(pedidoDto.toString().getBytes());
        //System.out.println(message);
        //rabbitTemplate.send("orders.v1.order-created", message);
        //rabbitTemplate.convertAndSend("orders.v1.order-created", pedidoDto);
        rabbitTemplate.convertAndSend(QUEUE_NAME, objectMapper.writeValueAsString(pedidoDto));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pedidoDto);

    }
}
