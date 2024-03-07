package nelsonssoares.ecomshoppingapi.commons.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoSubscriber {

//    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.queue.create-pedido}")
    public void sendPedido(@Payload String pedido) {
        System.out.println("Pedido"+pedido);
//        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
//        System.out.println("Pedido"+pedidoDTO);

    }

}
