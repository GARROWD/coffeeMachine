package com.garrow.coffeemachine.utils.exceptions.handlers;

import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import com.garrow.coffeemachine.utils.exceptions.messages.GenericMessage;
import com.garrow.coffeemachine.utils.exceptions.messages.GenericMessageTimestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionsHandler {

    // Не знаю стоит ли делать свой @JsonDeserialize(using = UuidDeserializer.class) и выбрасывать там свою ошибку, чтобы сделать красивое сообщение
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericMessage httpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        return new GenericMessage(exception.getLocalizedMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<GenericMessage> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream().map(error ->
                new GenericMessage(error.getDefaultMessage(), error.getField())
        ).toList();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericMessageTimestamp customerNotFoundException(NotFoundException exception) {
        return new GenericMessageTimestamp(exception.getMessage());
    }
}

