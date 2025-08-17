package br.hendrew.goldenraspberryapi.service;

import br.hendrew.goldenraspberryapi.calculator.ProducerIntervalCalculator;
import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ProducerIntervalCalculator intervalCalculator;


    public IntervalResponseDTO calculateProducerIntervals() {
        List<Movie> winners = movieRepository.findByWinnerTrueOrderByProducersAscYearAsc();
        return intervalCalculator.calculate(winners);
    }
}
