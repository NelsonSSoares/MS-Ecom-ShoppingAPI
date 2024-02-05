package nelsonssoares.ecomshoppingapi.usecases;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.constraints.Constraints;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderByStatus {

    private final PedidoRepository pedidoRepository;

    public List<PedidoResponse> executeGetOrderByStatus(StatusPedido statusPedido){

        List<Pedido> pedidos = pedidoRepository.findAllByStatusPedido(statusPedido);

        List<Pedido> pedidosAtivos = Constraints.pedidoAtivoList(pedidos);

        System.out.println(pedidosAtivos);


        return null;
    }
}
