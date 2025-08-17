package br.hendrew.goldenraspberryapi.service;

import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.exception.CsvProcessingException;
import br.hendrew.goldenraspberryapi.parse.CsvMovieParser;
import br.hendrew.goldenraspberryapi.properties.CsvMovieProperties;
import br.hendrew.goldenraspberryapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvLoaderService implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final CsvMovieParser csvMovieParser;
    private final CsvMovieProperties properties;

    @Override
    public void run(String... args) {
        File file = new File(properties.getDirectory());
        if (!file.exists()) {
            throw new CsvProcessingException("Arquivo CSV não encontrado em: " + properties.getDirectory());
        }
        List<Movie> movies = csvMovieParser.parse(file);
        movieRepository.saveAll(movies);
    }
}
