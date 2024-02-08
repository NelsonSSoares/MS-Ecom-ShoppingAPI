package nelsonssoares.ecomshoppingapi.usecases;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PatchOrderStatus {

    private final PedidoRepository pedidoRepository;

    public Pedido executePathOrderStatus(Integer id, Pedido status){
        System.out.println(status);
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            return null;
        }
        pedido.setDataModificacao(LocalDate.now());
        pedido.setStatusPedido(status.getStatusPedido());
        return pedidoRepository.save(pedido);
    }

}
