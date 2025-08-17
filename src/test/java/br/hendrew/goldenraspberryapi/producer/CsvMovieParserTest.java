package br.hendrew.goldenraspberryapi.producer;

import br.hendrew.goldenraspberryapi.dto.MovieDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.exception.CsvProcessingException;
import br.hendrew.goldenraspberryapi.mapper.MovieMapper;
import br.hendrew.goldenraspberryapi.parse.CsvMovieParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvMovieParserTest {
    @Spy
    private MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    @InjectMocks
    private CsvMovieParser parser;

    @Test
    void parse_shouldReturnMovies_whenCsvIsValid() throws IOException {
        File tempFile = File.createTempFile("movies", ".csv");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("year;title;studios;producers;winner\n");
            writer.write("2000;Movie 1;Studio 1;Producer A;yes\n");
            writer.write("2005;Movie 2;Studio 2;Producer B;\n");
        }

        List<Movie> movies = parser.parse(tempFile);

        assertThat(movies).hasSize(2);
        assertThat(movies.get(0).getTitle()).isEqualTo("Movie 1");
        assertThat(movies.get(1).getProducers()).isEqualTo("Producer B");

        verify(mapper, times(2)).toEntity(any(MovieDTO.class));
    }

    @Test
    void parse_shouldReturnEmptyList_whenCsvIsEmpty() throws IOException {
        File tempFile = File.createTempFile("empty", ".csv");
        tempFile.deleteOnExit();

        List<Movie> movies = parser.parse(tempFile);
        assertThat(movies).isEmpty();

        verify(mapper, never()).toEntity(any(MovieDTO.class));
    }

    @Test
    void parse_shouldThrowException_whenYearIsInvalid() throws IOException {
        File tempFile = File.createTempFile("invalidYear", ".csv");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("year;title;studios;producers;winner\n");
            writer.write("INVALID;Movie 1;Studio 1;Producer A;yes\n");
        }

        assertThatThrownBy(() -> parser.parse(tempFile))
                .isInstanceOf(CsvProcessingException.class)
                .hasMessageContaining("Ano inválido");

        verify(mapper, never()).toEntity(any(MovieDTO.class));
    }

    @Test
    void parse_shouldIgnoreHeaderLine() throws IOException {
        File tempFile = File.createTempFile("headerTest", ".csv");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("year;title;studios;producers;winner\n");
            writer.write("2010;Movie X;Studio X;Producer X;yes\n");
        }

        List<Movie> movies = parser.parse(tempFile);
        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getTitle()).isEqualTo("Movie X");

        verify(mapper).toEntity(any(MovieDTO.class));
    }
}
