package com.alkemy.ong.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordException {

    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public static final class NotDeletedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public NotDeletedException(String message) {

            super(message);
        }
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class RecordNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public RecordNotFoundException(String message) {

            super(message);
        }
    }
}
