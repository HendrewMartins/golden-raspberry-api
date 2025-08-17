package br.hendrew.goldenraspberryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo os intervalos mínimos e máximos de prêmios por produtor")
public class IntervalResponseDTO {

    @Schema(description = "Lista de produtores com menor intervalo entre prêmios")
    private List<ProducerIntervalDTO> min;

    @Schema(description = "Lista de produtores com maior intervalo entre prêmios")
    private List<ProducerIntervalDTO> max;
}
