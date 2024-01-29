package nelsonssoares.ecomshoppingapi.domain.repositories;

import nelsonssoares.ecomshoppingapi.domain.entities.DetalhesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalhesPedidoRepository extends JpaRepository<DetalhesPedido, Integer> {
}
