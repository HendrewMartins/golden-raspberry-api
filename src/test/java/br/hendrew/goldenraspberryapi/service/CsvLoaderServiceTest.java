package br.hendrew.goldenraspberryapi.service;

import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.exception.CsvProcessingException;
import br.hendrew.goldenraspberryapi.parse.CsvMovieParser;
import br.hendrew.goldenraspberryapi.properties.CsvMovieProperties;
import br.hendrew.goldenraspberryapi.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvLoaderServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CsvMovieParser csvMovieParser;
    @Mock
    private CsvMovieProperties properties;
    @InjectMocks
    private CsvLoaderService loaderService;

    @Test
    void run_shouldThrowException_whenFileDoesNotExist() {
        when(properties.getDirectory()).thenReturn("nonexistent.csv");

        assertThatThrownBy(() -> loaderService.run())
                .isInstanceOf(CsvProcessingException.class)
                .hasMessageContaining("Arquivo CSV não encontrado");

        verifyNoInteractions(csvMovieParser, movieRepository);
    }

    @Test
    void run_shouldParseAndSaveMovies_whenFileExists() throws IOException {
        File tempFile = File.createTempFile("movies", ".csv");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("2000;Movie 1;Studio 1;Producer A;yes\n");
            writer.write("2005;Movie 2;Studio 2;Producer B;\n");
        }

        when(properties.getDirectory()).thenReturn(tempFile.getAbsolutePath());


        Movie movie1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        Movie movie2 = new Movie(2005, "Movie 2", "Studio 2", "Producer B", false);
        when(csvMovieParser.parse(tempFile)).thenReturn(List.of(movie1, movie2));

        loaderService.run();

        verify(csvMovieParser).parse(tempFile);
        verify(movieRepository).saveAll(List.of(movie1, movie2));
    }
}
