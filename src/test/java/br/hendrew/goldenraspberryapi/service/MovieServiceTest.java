package br.hendrew.goldenraspberryapi.service;

import br.hendrew.goldenraspberryapi.calculator.ProducerIntervalCalculator;
import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.dto.ProducerIntervalDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ProducerIntervalCalculator intervalCalculator;
    @InjectMocks
    private MovieService movieService;

    @Test
    void calculateProducerIntervals_shouldReturnCalculatedResult() {
        Movie movie1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        Movie movie2 = new Movie(2005, "Movie 2", "Studio 2", "Producer A", true);
        List<Movie> winners = List.of(movie1, movie2);

        when(movieRepository.findByWinnerTrueOrderByProducersAscYearAsc()).thenReturn(winners);

        ProducerIntervalDTO dto = new ProducerIntervalDTO("Producer A", 5, 2000, 2005);
        IntervalResponseDTO expected = new IntervalResponseDTO(List.of(dto), List.of(dto));
        when(intervalCalculator.calculate(winners)).thenReturn(expected);


        IntervalResponseDTO result = movieService.calculateProducerIntervals();


        assertThat(result).isEqualTo(expected);
        verify(movieRepository).findByWinnerTrueOrderByProducersAscYearAsc();
        verify(intervalCalculator).calculate(winners);
    }

    @Test
    void calculateProducerIntervals_shouldReturnException() {
        when(movieRepository.findByWinnerTrueOrderByProducersAscYearAsc()).thenThrow(new RuntimeException("Erro simulado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> movieService.calculateProducerIntervals());

        assertEquals("Erro simulado", exception.getMessage());
        verify(movieRepository).findByWinnerTrueOrderByProducersAscYearAsc();
        verifyNoInteractions(intervalCalculator);
    }
}
