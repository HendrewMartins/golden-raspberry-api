package br.hendrew.goldenraspberryapi.repository;

import br.hendrew.goldenraspberryapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByWinnerTrueOrderByProducersAscYearAsc();
}
