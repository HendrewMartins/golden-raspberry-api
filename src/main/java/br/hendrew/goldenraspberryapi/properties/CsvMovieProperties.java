package br.hendrew.goldenraspberryapi.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "csv-movie")
public class CsvMovieProperties {
    private String directory;
}
