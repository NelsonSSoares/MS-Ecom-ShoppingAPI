package nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.enums.PerguntaAtivo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    private Integer id;

    private String apelido;

    private String rua;

    private String numero;

    private String complemento;

    private String cep;

    private String bairro;

    private String cidade;

    private String estado;

    private String pais;

    private PerguntaAtivo enderecoPadrao;
}
