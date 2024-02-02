package nelsonssoares.ecomshoppingapi.domain.repositories;

import feign.Param;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query(value = "select * from pedidos p where p.usuario_id = :id", nativeQuery = true)
    List<Pedido> findAllByUsuarioId(@Param("id") Integer id);
}
