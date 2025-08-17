package br.hendrew.goldenraspberryapi.mapper;

import br.hendrew.goldenraspberryapi.dto.MovieDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovieMapperImplTest {

    private MovieMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new MovieMapperImpl();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        MovieDTO dto = new MovieDTO(2025, "Movie Title", "Studio X", "Producer Y", true);

        Movie movie = mapper.toEntity(dto);

        assertThat(movie).isNotNull();
        assertThat(movie.getYear()).isEqualTo(2025);
        assertThat(movie.getTitle()).isEqualTo("Movie Title");
        assertThat(movie.getStudios()).isEqualTo("Studio X");
        assertThat(movie.getProducers()).isEqualTo("Producer Y");
        assertThat(movie.isWinner()).isTrue();
    }

    @Test
    void toEntity_shouldHandleNullDto() {
        Movie movie = mapper.toEntity(null);
        assertThat(movie).isNull();
    }

    @Test
    void toEntity_shouldHandleNullYear() {
        MovieDTO dto = new MovieDTO(null, "Movie Title", "Studio X", "Producer Y", false);

        Movie movie = mapper.toEntity(dto);

        assertThat(movie).isNotNull();
        assertThat(movie.getYear()).isZero();
        assertThat(movie.getTitle()).isEqualTo("Movie Title");
        assertThat(movie.getStudios()).isEqualTo("Studio X");
        assertThat(movie.getProducers()).isEqualTo("Producer Y");
        assertThat(movie.isWinner()).isFalse();
    }
}
