package com.example.tasktestravenapplicationtwo.advices;

import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionAdviceTest {
    @Test
    public void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        GlobalExceptionAdvice advice = new GlobalExceptionAdvice();
        Map<String, String> errors = advice.handleValidationExceptions(ex);

        assertEquals(1, errors.size());
        assertEquals("defaultMessage", errors.get("field"));
    }

    @Test
    public void testHandleConstraintViolationException() {
        String errorMessage = "ConstraintViolationException occurred";
        ConstraintViolationException ex = new ConstraintViolationException(errorMessage, null);

        GlobalExceptionAdvice advice = new GlobalExceptionAdvice();
        Map<String, String> errors = advice.handleConstraintViolationException(ex);

        assertEquals(1, errors.size());
        assertEquals(errorMessage, errors.get("error"));
    }

    @Test
    public void testHandleCustomerException() {
        String errorMessage = "CustomerException occurred";
        CustomerException ex = new CustomerException(errorMessage);

        GlobalExceptionAdvice advice = new GlobalExceptionAdvice();
        Map<String, String> errors = advice.handleCustomerException(ex);

        assertEquals(1, errors.size());
        assertEquals(errorMessage, errors.get("error"));
    }

    @Test
    public void testHandleAllOtherExceptions() {
        String errorMessage = "Unexpected error occurred";
        Exception ex = new Exception(errorMessage);

        GlobalExceptionAdvice advice = new GlobalExceptionAdvice();
        Map<String, String> errors = advice.handleAllOtherExceptions(ex);

        assertEquals(1, errors.size());
        assertEquals(errorMessage, errors.get("error"));
    }
}