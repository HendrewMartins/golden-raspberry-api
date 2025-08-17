package br.hendrew.goldenraspberryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informações sobre o intervalo de prêmios de um produtor")
public class ProducerIntervalDTO {

    @Schema(description = "Nome do produtor", example = "Producer 1")
    private String producer;

    @Schema(description = "Intervalo em anos entre dois prêmios consecutivos", example = "5")
    private int interval;

    @Schema(description = "Ano do prêmio anterior", example = "1980")
    private int previousWin;

    @Schema(description = "Ano do prêmio seguinte", example = "1985")
    private int followingWin;
}
