package nelsonssoares.ecomshoppingapi.outlayers.gateways;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.enums.PerguntaAtivo;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.UsuarioClient;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Endereco;
import nelsonssoares.ecomshoppingapi.outlayers.gateways.clients.entities.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioGateway {

    private final UsuarioClient usuarioClient;

    public Usuario findById(Integer id) {

        ResponseEntity<Usuario> usuariorequest = usuarioClient.findById(id);

        System.out.println(usuariorequest.getBody());
        if (usuariorequest.getBody() == null || usuariorequest.getStatusCode().is4xxClientError()|| !usuariorequest.hasBody()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }

        return usuariorequest.getBody();

    }


    public Endereco findAddressByUserId(Integer id) {

        ResponseEntity<List<Endereco>> enderecoRequest = usuarioClient.findAddressByUserId(id);

        if (enderecoRequest.getBody() == null || enderecoRequest.getStatusCode().is4xxClientError() || !enderecoRequest.hasBody()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }

        Endereco endereco = enderecoRequest.getBody().stream().filter(end -> end.getEnderecoPadrao().equals(PerguntaAtivo.SIM)).findFirst().orElse(null);
        if (endereco == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço ativo não encontrado");
        }

        return endereco;
    }
}
