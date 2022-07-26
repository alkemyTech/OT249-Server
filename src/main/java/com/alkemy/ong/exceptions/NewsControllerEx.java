package com.alkemy.ong.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class NewsControllerEx extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BindingResultException.class)
    public ResponseEntity<?> handleValidateExceptions(BindingResultException ex) {
        Map<String, Object> tokens = new HashMap<>();
        List<Map<String, Object>> token2 = ex.getFieldErrors().stream().map( vale -> {
            String defaultMessage = "FIELD ERROR";
            String code = "CODE ERROR";
            if (vale.getDefaultMessage() != null)
                defaultMessage = vale.getDefaultMessage();
            if (vale.getCode() != null)
                code = vale.getCode();
            return Map.<String, Object>ofEntries(
                    Map.entry( "message", defaultMessage ),
                    Map.entry( "code", code ),
                    Map.entry( "field", vale.getField() )
            );
        } ).collect( Collectors.toList() );

        tokens.put( "errorMessage", "Hay errores en lo enviado" );
        tokens.put( "errorFields", token2 );
        tokens.put( "errorCode", BAD_REQUEST.series() );
        return new ResponseEntity<>( tokens, BAD_REQUEST );

    }

    @ExceptionHandler(RecordException.RecordNotFoundException.class)
    public Map<String, String> handleValidateExceptionsEmail(RecordException.RecordNotFoundException ex) {
        return Map.of("errors", ex.getMessage());
    }

}
