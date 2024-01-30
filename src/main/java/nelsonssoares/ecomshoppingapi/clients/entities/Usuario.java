package nelsonssoares.ecomshoppingapi.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    private Integer id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String telefone;

    private String email;


}
