package br.hendrew.goldenraspberryapi.exception;


import br.hendrew.goldenraspberryapi.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }


    @Test
    void handleRuntimeException_shouldReturnInternalServerError() {
        RuntimeException ex = new RuntimeException("Erro inesperado");

        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro inesperado", response.getBody().getMessage());
    }

    @Test
    void handleEntityNotFoundException_shouldReturnNotFound() {
        EntityNotFoundException ex = new EntityNotFoundException("Usuário não encontrado");

        ResponseEntity<ErrorResponse> response = handler.handleEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody().getMessage());
        assertEquals("NOT_ENTITY", response.getBody().getError());
    }

    @Test
    void handleMethodArgumentNotValidException_withMethodArgumentNotValid_shouldReturnValidationError() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "changeUserStatusRequest");
        bindingResult.addError(new FieldError("changeUserStatusRequest", "status", "O status é obrigatório"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O status é obrigatório", response.getBody().getMessage());
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleConstraintViolationException_withConstraintViolationException_shouldReturnValidationError() {
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Campo inválido");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

        ResponseEntity<ErrorResponse> response = handler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo inválido", response.getBody().getMessage());
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
    }

    @Test
    void handleIllegalArgumentException_shouldReturnBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Parâmetro inválido");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parâmetro inválido", response.getBody().getMessage());
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
    }

    @Test
    void handleCsvProcessingException_shouldReturnUnprocessable_Entity() {
        CsvProcessingException ex = new CsvProcessingException("Parâmetro inválido");

        ResponseEntity<ErrorResponse> response = handler.handleCsvProcessingException(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Parâmetro inválido", response.getBody().getMessage());
        assertEquals("CSV_PROCESSING_ERROR", response.getBody().getError());
    }
}
