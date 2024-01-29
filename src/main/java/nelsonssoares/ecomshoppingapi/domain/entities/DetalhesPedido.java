package nelsonssoares.ecomshoppingapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nelsonssoares.ecomshoppingapi.clients.entities.Produto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalhes_pedido")
public class DetalhesPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name = "produto")
    private Produto produto;

    @NotNull(message = "Este campo não pode ser nulou, deve conter números positivos!")
    private Integer quantidade;

    @NotNull(message = "Preco deve ser informado! Numero positivo!")
    @Column(name = "preco_total", precision = 20, scale = 2)
    private BigDecimal precoTotal;
}