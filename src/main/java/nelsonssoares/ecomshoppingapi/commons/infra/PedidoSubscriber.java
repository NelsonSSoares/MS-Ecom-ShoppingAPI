package nelsonssoares.ecomshoppingapi.commons.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.usecases.SaveOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoSubscriber {

    private final ObjectMapper objectMapper;
    private final SaveOrder saveOrder;

    @RabbitListener(queues = "${mq.queue.create-pedido}")
    public void sendPedido(@Payload String pedido) {
        System.out.println("Pedido Listener" + pedido);
        try {
            PedidoDTO pedidoDTO = objectMapper.readValue(pedido, PedidoDTO.class);
            System.out.println("Pedido: " + pedidoDTO.toString());
            saveOrder.executeSaveOrder(pedidoDTO);
            //IMPLEMENTAR ENVIO DE EMAIL DE SUCESSO
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
