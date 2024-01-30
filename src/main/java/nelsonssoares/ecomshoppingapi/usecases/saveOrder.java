package nelsonssoares.ecomshoppingapi.usecases;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class saveOrder {

    private final PedidoRepository pedidoRepository;

}
