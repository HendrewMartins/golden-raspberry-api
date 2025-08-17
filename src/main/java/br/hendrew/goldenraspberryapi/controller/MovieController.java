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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
@Tag(name = "Movie", description = "End Point referente a gestão de filmes")
public class MovieController {

    private final MovieService movieService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload de arquivo CSV",
            description = """
                    Permite o envio de um arquivo CSV contendo os filmes.
                    O arquivo deve ter as colunas: year, title, studios, producers, winner.
                """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo processado e filmes salvos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato inválido ou erro ao processar o arquivo"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar o upload")
    })
    public ResponseEntity<Void> uploadMovies(@RequestParam("file") MultipartFile file) {
        movieService.processCsv(file);
        return ResponseEntity.noContent().build();
    }


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
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição")
    })
    public ResponseEntity<IntervalResponseDTO> getProducerIntervals() {
        return ResponseEntity.ok(movieService.calculateProducerIntervals());
    }
}
