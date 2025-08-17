package br.hendrew.goldenraspberryapi.parse;

import br.hendrew.goldenraspberryapi.dto.MovieDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import br.hendrew.goldenraspberryapi.exception.CsvProcessingException;
import br.hendrew.goldenraspberryapi.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvMovieParser {

    private final MovieMapper movieMapper;

    public List<Movie> parse(File file) {
        try (Reader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            return processCsv(br);

        } catch (IOException e) {
            throw new CsvProcessingException("Erro ao processar CSV: " + file.getName(), e);
        } catch (NumberFormatException e) {
            throw new CsvProcessingException("Ano inválido no CSV: " + file.getName(), e);
        }
    }

    private List<Movie> processCsv(BufferedReader br) throws IOException {
        List<Movie> movies = new ArrayList<>();
        String line;
        boolean isFirstLine = true;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            if (isFirstLine && isHeader(line)) {
                isFirstLine = false;
                continue;
            }

            MovieDTO dto = parseLineToDto(line);
            movies.add(movieMapper.toEntity(dto));
        }

        return movies;
    }

    private boolean isHeader(String line) {
        String lower = line.toLowerCase();
        return lower.contains("year") && lower.contains("title");
    }

    private MovieDTO parseLineToDto(String line) {
        String[] columns = line.split(";", -1);
        if (columns.length < 4) return null;

        int year = Integer.parseInt(columns[0].trim());
        String title = columns[1].trim();
        String studios = columns[2].trim();
        String producers = columns[3].trim();
        boolean winner = columns.length > 4 && "yes".equalsIgnoreCase(columns[4].trim());

        return new MovieDTO(year, title, studios, producers, winner);
    }
}
