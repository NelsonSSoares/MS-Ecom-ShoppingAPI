package nelsonssoares.ecomshoppingapi.commons.configs;

import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${mq.queue.create-pedido}")
    private String queueName;

    @Bean
    public Queue createQueue() {
        return new Queue(queueName, true);
    }


}
