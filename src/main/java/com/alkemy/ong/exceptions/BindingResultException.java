package com.alkemy.ong.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BindingResultException extends RuntimeException {

    List<FieldError> fieldErrors;

    public BindingResultException(BindingResult bindingResult) {

        this.fieldErrors = new ArrayList<>( bindingResult.getFieldErrors() );
    }
}
