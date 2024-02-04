package nelsonssoares.ecomshoppingapi.usecases;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderByStatus {

    private final PedidoRepository pedidoRepository;

    public List<PedidoResponse> executeGetOrderByStatus(StatusPedido statusPedido){

        List<PedidoResponse> pedidos = pedidoRepository.findAllByStatusPedido(statusPedido);


        return null;
    }
}
