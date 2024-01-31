package nelsonssoares.ecomshoppingapi.entrypoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoDTO;
import nelsonssoares.ecomshoppingapi.domain.dtos.PedidoResponse;
import nelsonssoares.ecomshoppingapi.domain.entities.Pedido;
import nelsonssoares.ecomshoppingapi.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nelsonssoares.ecomshoppingapi.commons.constants.ControllerConstants.*;

@Tag(name = API_TAG, description = API_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = API_BASE_URL, produces = API_PRODUCES)
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Metodo para cadastrar novo Pedido", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadstrado com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponse> save(@RequestBody @Valid PedidoDTO dto) {
        return pedidoService.save(dto);
    }

}
