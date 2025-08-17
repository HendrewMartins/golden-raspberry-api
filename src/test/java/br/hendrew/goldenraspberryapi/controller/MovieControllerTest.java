package br.hendrew.goldenraspberryapi.controller;

import br.hendrew.goldenraspberryapi.dto.IntervalResponseDTO;
import br.hendrew.goldenraspberryapi.exception.GlobalExceptionHandler;
import br.hendrew.goldenraspberryapi.seed.IntervalResponseSeed;
import br.hendrew.goldenraspberryapi.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    private IntervalResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        responseDTO = IntervalResponseSeed.create();
    }

    @Test
    void getProducerIntervals_shouldReturnSuccess() throws Exception {
        when(movieService.calculateProducerIntervals()).thenReturn(responseDTO);

        mockMvc.perform(get("/v1/movie/producers/intervals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())
                .andExpect(jsonPath("$.min[0].producer").exists())
                .andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
                .andExpect(jsonPath("$.max[0].producer").exists())
                .andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"));

        verify(movieService).calculateProducerIntervals();
    }

    @Test
    void getProducerIntervals_shouldReturnInternalServerError_whenServiceThrows() throws Exception {
        doThrow(new RuntimeException("Erro simulado")).when(movieService).calculateProducerIntervals();

        mockMvc.perform(get("/v1/movie/producers/intervals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Erro simulado"))
                .andExpect(jsonPath("$.code").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        verify(movieService).calculateProducerIntervals();
    }
}
