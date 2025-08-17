package br.hendrew.goldenraspberryapi.calculator;

import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.dto.ProducerIntervalDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProducerIntervalCalculatorTest {

    @InjectMocks
    private ProducerIntervalCalculator calculator;

    @Test
    void calculate_shouldReturnEmptyLists_whenNoWinners() {
        IntervalResponseDTO result = calculator.calculate(List.of());
        assertThat(result.getMin()).isEmpty();
        assertThat(result.getMax()).isEmpty();
    }

    @Test
    void calculate_shouldReturnEmptyLists_whenSingleWinnerPerProducer() {
        Movie movie = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        IntervalResponseDTO result = calculator.calculate(List.of(movie));
        assertThat(result.getMin()).isEmpty();
        assertThat(result.getMax()).isEmpty();
    }

    @Test
    void calculate_shouldComputeIntervals_forSingleProducer() {
        Movie m1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        Movie m2 = new Movie(2005, "Movie 2", "Studio 2", "Producer A", true);

        IntervalResponseDTO result = calculator.calculate(List.of(m1, m2));

        assertThat(result.getMin()).hasSize(1);
        assertThat(result.getMax()).hasSize(1);

        ProducerIntervalDTO interval = result.getMin().get(0);
        assertThat(interval.getProducer()).isEqualTo("Producer A");
        assertThat(interval.getInterval()).isEqualTo(5);
        assertThat(interval.getPreviousWin()).isEqualTo(2000);
        assertThat(interval.getFollowingWin()).isEqualTo(2005);
    }

    @Test
    void calculate_shouldComputeIntervals_forMultipleProducers() {
        Movie m1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        Movie m2 = new Movie(2005, "Movie 2", "Studio A", "Producer A", true);
        Movie m3 = new Movie(2000, "Movie 3", "Studio B", "Producer B", true);
        Movie m4 = new Movie(2005, "Movie 4", "Studio B", "Producer B", true);

        IntervalResponseDTO result = calculator.calculate(List.of(m1, m2, m3, m4));

        assertThat(result.getMin()).hasSize(2);
        assertThat(result.getMax()).hasSize(2);

        assertThat(result.getMin())
                .extracting(ProducerIntervalDTO::getProducer)
                .containsExactlyInAnyOrder("Producer A", "Producer B");

        assertThat(result.getMax())
                .extracting(ProducerIntervalDTO::getInterval)
                .allMatch(i -> i == 5);
    }

    @Test
    void calculate_shouldSplitMultipleProducers_inSingleMovie() {
        Movie m1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A, Producer B and Producer C", true);
        Movie m2 = new Movie(2002, "Movie 2", "Studio 2", "Producer A, Producer B", true);
        Movie m3 = new Movie(2002, "Movie 3", "Studio 3", "Producer C", true);

        IntervalResponseDTO result = calculator.calculate(List.of(m1, m2, m3));

        assertThat(result.getMin()).hasSizeGreaterThan(2);
        assertThat(result.getMax()).hasSizeGreaterThan(2);

        assertThat(result.getMin())
                .extracting(ProducerIntervalDTO::getProducer)
                .contains("Producer A", "Producer B", "Producer C");
    }
}
