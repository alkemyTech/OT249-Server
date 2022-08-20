package com.alkemy.ong.exceptions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerExTest {

    /**
     * Method under test: {@link UserControllerEx#handleValidateExceptions(MethodArgumentNotValidException)}
     */
    @Test
    void testHandleValidateExceptions() {

        UserControllerEx userControllerEx = new UserControllerEx();
        BindingResult bindingResult = mock( BindingResult.class );
        ArrayList<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add( new FieldError("hoo","field", "error") );
        when(bindingResult.getAllErrors()  ).thenReturn( objectErrors );
        Map<String, String> handleValidateExceptions = userControllerEx
                .handleValidateExceptions( new MethodArgumentNotValidException( mock( MethodParameter.class ), bindingResult ) );
        assertThat( handleValidateExceptions ).isNotNull().isNotEmpty().hasSize( 1 );
    }

    /**
     * Method under test: {@link UserControllerEx#handleValidateExceptions(MethodArgumentNotValidException)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleValidateExceptions2() {
        // TODO: Complete this test.

        (new UserControllerEx()).handleValidateExceptions( null );
    }

    /**
     * Method under test: {@link UserControllerEx#handleValidateExceptionsEmail(SQLIntegrityConstraintViolationException)}
     */
    @Test
    void testHandleValidateExceptionsEmail() {

        Map<String, String> actualHandleValidateExceptionsEmailResult = (new UserControllerEx())
                .handleValidateExceptionsEmail( null );
        assertEquals( 1, actualHandleValidateExceptionsEmailResult.size() );
        assertEquals( "Email digitado esta en uso", actualHandleValidateExceptionsEmailResult.get( "Error" ) );
    }
}

