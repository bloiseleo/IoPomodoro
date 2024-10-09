package io.pomodoro.exceptions;

import io.pomodoro.dtos.BaseResponseDTO;
import io.pomodoro.dtos.InternalErrorResponseDTO;
import io.pomodoro.dtos.SuccessResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalErrorResponseDTO> handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new InternalErrorResponseDTO("Internal Server Error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(BaseAPIException.class)
    public ResponseEntity<BaseResponseDTO> handleBaseAPIException(BaseAPIException exception) {
        return new ResponseEntity<>(
                new SuccessResponseDTO(exception.getMessage(), exception.getStatus()),
                exception.getStatus()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDTO> hanBaseResponseDTOResponseEntity(MethodArgumentNotValidException exception) {
        var statusCode = exception.getStatusCode();
        var messages = exception.getDetailMessageArguments();
        var treatedMessages = Arrays.stream(messages).map(message -> message.toString())
                .filter(message -> !message.isBlank()).toList();
        return new ResponseEntity<>(
                new SuccessResponseDTO(treatedMessages.stream().findFirst().get(), HttpStatus.valueOf(statusCode.value())),
                statusCode
        );
    }
}
