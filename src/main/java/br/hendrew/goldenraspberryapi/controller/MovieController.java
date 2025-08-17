package br.hendrew.goldenraspberryapi.controller;

import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
@Tag(name = "Movie", description = "End Point referente a gestão de filmes")
public class MovieController {

    private final MovieService movieService;


    @GetMapping(value = "/producers/intervals", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recuperar intervalos de prêmios dos produtores",
            description = """
                        Retorna os produtores com o menor e maior intervalo entre dois prêmios consecutivos.
                        A resposta contém duas listas:
                        - **min**: produtores com o menor intervalo
                        - **max**: produtores com o maior intervalo
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervalos de produtores retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    public ResponseEntity<IntervalResponseDTO> getProducerIntervals() {
        return ResponseEntity.ok(movieService.calculateProducerIntervals());
    }
}
