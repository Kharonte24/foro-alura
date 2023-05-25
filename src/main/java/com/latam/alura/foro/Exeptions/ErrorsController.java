package com.latam.alura.foro.Exeptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.latam.alura.foro.DAO.Errores.ErrorAPI;
import com.latam.alura.foro.DAO.Errores.ErrorValidacion;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorsController {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            EntityNotFoundException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorAPI> handleNotFoundException(
            Exception e, HttpServletRequest request){
        return buildErrorResponse(request, HttpStatus.NOT_FOUND, "No se puede Encontrar el Recurso Solicitado");
    }

    @ExceptionHandler({DuplicateResourceException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<ErrorAPI> handleInvalidDataException(
            Exception e, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({InsufficientAuthenticationException.class, TopicoClosedException.class})
    public ResponseEntity<ErrorAPI> handleForbiddenException(
            InsufficientAuthenticationException e, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorAPI> handleUnauthorizedException(
            BadCredentialsException e, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorAPI> handleValidationException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        List<ErrorValidacion> errors = e.getFieldErrors().stream()
                .map(fieldError -> new ErrorValidacion(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorAPI apiError = new ErrorAPI(
                request.getRequestURI(),
                "La Validacion Fallo por uno o mas campos",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errors);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorAPI> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, HttpServletRequest request) {

        List<ErrorValidacion> errors = new ArrayList<>();

        if (e.getRootCause() instanceof InvalidFormatException exception){

            errors.add(new ErrorValidacion(exception.getPath().get(0).getFieldName(),
                    "Invalid value: '" + exception.getValue() + "'"));


        }else {
            errors.add(new ErrorValidacion("requestBody", "Invalid request body"));
        }


        ErrorAPI apiError = new ErrorAPI(
                request.getRequestURI(),
                "Validation failed for one or more fields",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errors);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorAPI> handleDataIntegrityViolationException(
            DataIntegrityViolationException e, HttpServletRequest request){
        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorAPI> handleException(Exception e, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }



    private ResponseEntity<ErrorAPI> buildErrorResponse(
            HttpServletRequest request, HttpStatus status, String mensaje) {

        ErrorAPI errorAPI = new ErrorAPI(
                request.getRequestURI(),
                mensaje,
                status.value(),
                LocalDateTime.now(),
                null);

        return new ResponseEntity<>(errorAPI, status);

    }
}
