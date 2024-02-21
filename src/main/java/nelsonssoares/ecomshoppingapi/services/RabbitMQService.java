package nelsonssoares.ecomshoppingapi.services;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQService.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;


    public void sendMessage(PedidoDTO object) {
        LOGGER.info("Enviando mensagem para o RabbitMQ, Mensagem: -> %s", object);
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

}
