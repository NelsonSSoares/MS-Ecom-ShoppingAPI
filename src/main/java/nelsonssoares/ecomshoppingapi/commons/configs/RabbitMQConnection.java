package nelsonssoares.ecomshoppingapi.commons.configs;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Binding;

import static nelsonssoares.ecomshoppingapi.commons.constants.RabbitMQConstants.EXCHANGE_NAME;
import static nelsonssoares.ecomshoppingapi.commons.constants.RabbitMQConstants.QUEUE_NAME;

@Component
@RequiredArgsConstructor
public class RabbitMQConnection {

    private final AmqpAdmin amqpAdmin;

    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue filaPedido = this.fila(QUEUE_NAME);
        DirectExchange troca = this.trocaDireta();

        Binding relacionamento = this.relacionamento(filaPedido, troca);

        this.amqpAdmin.declareQueue(filaPedido);
        this.amqpAdmin.declareExchange(troca);
        this.amqpAdmin.declareBinding(relacionamento);
    }
}
