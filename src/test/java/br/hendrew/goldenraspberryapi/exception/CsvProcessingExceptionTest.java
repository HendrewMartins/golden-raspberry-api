package br.hendrew.goldenraspberryapi.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CsvProcessingExceptionTest {

    @Test
    void shouldStoreMessage() {
        String message = "CSV inválido";
        CsvProcessingException ex = new CsvProcessingException(message);

        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getCause()).isNull();
    }

    @Test
    void shouldStoreMessageAndCause() {
        String message = "Erro ao processar CSV";
        Throwable cause = new RuntimeException("Detalhes do erro");

        CsvProcessingException ex = new CsvProcessingException(message, cause);

        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}
