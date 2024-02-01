package nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto implements Serializable {

    private Integer id;

    private String nomeProduto;

    private BigDecimal valorUnitario;
}
