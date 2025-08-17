package br.hendrew.goldenraspberryapi.service;

import br.hendrew.goldenraspberryapi.calculator.ProducerIntervalCalculator;
import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.parse.CsvMovieParser;
import br.hendrew.goldenraspberryapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CsvMovieParser csvMovieParser;
    private final ProducerIntervalCalculator intervalCalculator;

    public void processCsv(MultipartFile file) {
        List<Movie> movies = csvMovieParser.parse(file);
        movieRepository.saveAll(movies);
    }

    public IntervalResponseDTO calculateProducerIntervals() {
        List<Movie> winners = movieRepository.findByWinnerTrueOrderByProducersAscYearAsc();
        return intervalCalculator.calculate(winners);
    }
}
