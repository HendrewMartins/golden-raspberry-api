package br.hendrew.goldenraspberryapi.seed;

import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.dto.ProducerIntervalDTO;

import java.util.List;

public class IntervalResponseSeed {

    public static IntervalResponseDTO create() {
        ProducerIntervalDTO minInterval = new ProducerIntervalDTO(
                "Joel Silver",
                1,
                1990,
                1991
        );

        ProducerIntervalDTO maxInterval = new ProducerIntervalDTO(
                "Matthew Vaughn",
                13,
                2002,
                2015
        );

        return new IntervalResponseDTO(
                List.of(minInterval),
                List.of(maxInterval)
        );
    }
}
