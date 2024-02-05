package nelsonssoares.ecomshoppingapi.domain.repositories;

import feign.Param;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query(value = "select * from pedidos p where p.usuario_id = :id", nativeQuery = true)
    List<Pedido> findAllByUsuarioId(@Param("id") Integer id);

    @Query(value = "select * from pedidos p where p.status_pedido = :status", nativeQuery = true)
    List<Pedido> findAllByStatusPedido(@Param("status") StatusPedido status);
}
