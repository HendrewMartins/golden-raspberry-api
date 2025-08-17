package br.hendrew.goldenraspberryapi.mapper;

import br.hendrew.goldenraspberryapi.dto.MovieDTO;
import br.hendrew.goldenraspberryapi.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toEntity(MovieDTO dto);

}
