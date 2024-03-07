package nelsonssoares.ecomshoppingapi.commons.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue createQueue;
    private final ObjectMapper objectMapper;

    public boolean sendPedido(PedidoDTO pedido) throws JsonProcessingException {
        System.out.println("Publisher Pedido: "+pedido.toString());
        var json = convertToJson(pedido);
        rabbitTemplate.convertAndSend(createQueue.getName(), json);
        return true;
    }

    private String convertToJson(PedidoDTO pedidoDTO) throws JsonProcessingException {
        System.out.println("Conversor Pedido: "+pedidoDTO.toString());
        return objectMapper.writeValueAsString(pedidoDTO);
    }


}
