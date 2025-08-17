package br.hendrew.goldenraspberryapi.calculator;

import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.dto.ProducerIntervalDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProducerIntervalCalculator {

    public IntervalResponseDTO calculate(List<Movie> winners) {
        Map<String, List<Integer>> producerWins = groupWinsByProducer(winners);
        List<ProducerIntervalDTO> intervals = buildIntervals(producerWins);

        int minInterval = intervals.stream().mapToInt(ProducerIntervalDTO::getInterval).min().orElse(0);
        int maxInterval = intervals.stream().mapToInt(ProducerIntervalDTO::getInterval).max().orElse(0);

        return new IntervalResponseDTO(
                filterByInterval(intervals, minInterval),
                filterByInterval(intervals, maxInterval)
        );
    }

    private Map<String, List<Integer>> groupWinsByProducer(List<Movie> winners) {
        Map<String, List<Integer>> producerWins = new HashMap<>();
        for (Movie movie : winners) {
            String[] producers = movie.getProducers().split(",| and ");
            for (String producer : producers) {
                producerWins
                        .computeIfAbsent(producer.trim(), k -> new ArrayList<>())
                        .add(movie.getYear());
            }
        }
        return producerWins;
    }

    private List<ProducerIntervalDTO> buildIntervals(Map<String, List<Integer>> producerWins) {
        List<ProducerIntervalDTO> intervals = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue();
            if (years.size() < 2) continue;

            for (int i = 1; i < years.size(); i++) {
                intervals.add(new ProducerIntervalDTO(
                        entry.getKey(),
                        years.get(i) - years.get(i - 1),
                        years.get(i - 1),
                        years.get(i)
                ));
            }
        }
        return intervals;
    }

    private List<ProducerIntervalDTO> filterByInterval(List<ProducerIntervalDTO> intervals, int value) {
        return intervals.stream()
                .filter(i -> i.getInterval() == value)
                .toList();
    }
}
