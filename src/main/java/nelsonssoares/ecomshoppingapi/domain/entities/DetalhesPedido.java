package nelsonssoares.ecomshoppingapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalhes_pedido")
public class DetalhesPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Id do Produto deve ser informado")
    private Integer produto;

    @NotNull(message = "Este campo não pode ser nulou, deve conter números positivos!")
    private Integer quantidade;

    @NotNull(message = "Preco deve ser informado! Numero positivo!")
    @Column(name = "preco_total", precision = 20, scale = 2)
    private BigDecimal precoTotal;

//    @ManyToOne
//    @JoinColumn(name = "pedido_id")
//    private Pedido pedido;
}
