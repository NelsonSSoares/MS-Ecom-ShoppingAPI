package nelsonssoares.ecomshoppingapi.constraints;

import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;

import java.util.Iterator;
import java.util.List;

public class Constraints {
    public static List<Pedido> pedidoAtivoList(List<Pedido> pedidos){
        Iterator<Pedido> iterator = pedidos.iterator();

        while(iterator.hasNext()) {
            Pedido pedido = iterator.next();
            if(pedido.getStatusPedido().equals(StatusPedido.CANCELADO)) {
                iterator.remove();
            }
        }
        return pedidos;
    }
}
