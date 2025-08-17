package br.hendrew.goldenraspberryapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String error;
    private int code;

    public ErrorResponse(RuntimeException exception) {
        this.message = exception.getMessage();
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
