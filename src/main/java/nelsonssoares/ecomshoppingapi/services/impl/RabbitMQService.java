package nelsonssoares.ecomshoppingapi.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDtoMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String nomeFila, PedidoDTO object) throws JsonProcessingException {

        PedidoDtoMessage pedidoDtoMessage = objectMapper.convertValue(object, PedidoDtoMessage.class);

        System.out.println("Enviando mensagem");
        System.out.println(object);
        this.rabbitTemplate.convertAndSend(nomeFila, pedidoDtoMessage);
    }


}
