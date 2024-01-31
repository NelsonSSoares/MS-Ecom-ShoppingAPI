package nelsonssoares.ecomshoppingapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nelsonssoares.ecomshoppingapi.clients.entities.Usuario;
import nelsonssoares.ecomshoppingapi.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Id do Usuario deve ser informado")
    private Integer usuarioId;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @NotNull
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;

    @NotNull(message = "Total do Pedido deve ser informado! Numero Positivo!")
    @Column(name ="total_pedido", precision = 20, scale = 2, length = 100)
    private BigDecimal totalPedido;

    @OneToMany(mappedBy = "id")
    private List<DetalhesPedido> detalhesPedido;

    @NotNull(message = "Id do Endereco deve ser informado")
    private Integer enderecoId;
}
