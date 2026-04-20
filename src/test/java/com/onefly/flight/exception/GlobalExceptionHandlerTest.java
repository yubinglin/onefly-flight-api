package com.onefly.flight.exception;

import com.onefly.flight.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleBusinessException_shouldReturnErrorResponse() {
        BusinessException ex = new BusinessException(-101, "route not supported");
        ApiResponse<Void> response = handler.handleBusinessException(ex);

        assertEquals(-101, response.getStatus());
        assertEquals("route not supported", response.getMsg());
        assertNull(response.getData());
    }

    @Test
    void handleValidationException_shouldReturnFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("request", "cid", "cid is required");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ApiResponse<Void> response = handler.handleValidationException(ex);

        assertEquals(-2, response.getStatus());
        assertTrue(response.getMsg().contains("cid is required"));
    }

    @Test
    void handleConstraintViolation_shouldReturnError() {
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("must not be blank");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));
        ApiResponse<Void> response = handler.handleConstraintViolation(ex);

        assertEquals(-2, response.getStatus());
    }

    @Test
    void handleMessageNotReadable_shouldReturnFormatError() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMessage()).thenReturn("parse error");

        ApiResponse<Void> response = handler.handleMessageNotReadable(ex);

        assertEquals(-2, response.getStatus());
        assertEquals("Incorrect message format", response.getMsg());
    }

    @Test
    void handleException_shouldReturnUnknownError() {
        Exception ex = new RuntimeException("unexpected");
        ApiResponse<Void> response = handler.handleException(ex);

        assertEquals(-1, response.getStatus());
        assertEquals("Unknown error in the system", response.getMsg());
    }
}
