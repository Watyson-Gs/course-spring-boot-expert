package org.project.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.project.handler.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler de exceções globais para controladores REST.
 * Captura e trata exceções lançadas pelos métodos de @RestController,
 * retornando respostas com situação HTTP apropriado e mensagens de erro em formato string.
 * <p>
 * SUMÁRIO: Intercepta exceções na camada web e retorna mensagens de erro simples como string para o cliente.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler para ConstraintViolationException.
     * Captura falhas de validação @... que ocorrem FORA do fluxo de @Valid em @RequestBody,
     * como validação de entidade acionada pelo JPA/Hibernate ou validação manual via Validator.
     * Formata as violações em uma string detalhada.
     * <p>
     * SUMÁRIO: Trata erros de validação de constraints (entidade/manual), retorna string 400 BAD REQUEST.
     *
     * @param ex      A exceção ConstraintViolationException capturada.
     * @param request O contexto da requisição web (adicionado para consistência, embora não usado no corpo aqui).
     * @return ResponseEntity contendo uma string formatada com detalhes das violações e situação 400 BAD REQUEST.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.debug("Requisição [{}]: Exceção ConstraintViolationException capturada.", request.getDescription(false), ex);

        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        String finalMessage = "Erro(s) de validação: " + errorMessage;
        log.warn(finalMessage);
        return new ResponseEntity<>(finalMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler para MethodArgumentNotValidException.
     * Captura falhas de validação @... em parâmetros de métodos @RestController anotados com @Valid,
     * mais comum para validação de DTOs @RequestBody.
     * Formata os erros de campo e erros globais em uma string detalhada.
     * <p>
     * SUMÁRIO: Trata erros de validação de argumento da função (@Valid em DTOs), retorna string 400 BAD REQUEST.
     *
     * @param ex      A exceção MethodArgumentNotValidException capturada.
     * @param request O contexto da requisição web (adicionado para consistência).
     * @return ResponseEntity contendo uma string formatada com detalhes dos erros de validação e status 400 BAD REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        log.debug("Requisição [{}]: Exceção MethodArgumentNotValidException capturada.", request.getDescription(false), ex);

        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return String.format("Campo '%s': %s", fieldError.getField(), fieldError.getDefaultMessage());
                    } else {
                        return error.getObjectName() + ": " + error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        String finalMessage;
        if (errorMessages.isEmpty()) {
            finalMessage = "Erro de validação desconhecido.";
            log.error("Erro de validação sem detalhes capturado para requisição [{}]:", request.getDescription(false), ex);
        } else {
            finalMessage = "Erros de validação: " + String.join("; ", errorMessages);
        }

        log.warn("Validação de argumento falhou para requisição [{}]: {}", request.getDescription(false), finalMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(finalMessage);
    }

    /**
     * Handler para Exception (fallback).
     * Captura quaisquer outras exceções não tratadas pelos handlers específicos.
     * Atua como um fallback para erros inesperados da aplicação.
     * Deve ser o último handler na ordem de declaração.
     * <p>
     * SUMÁRIO: Trata erros inesperados genéricos, retorna string 500 INTERNAL SERVER ERROR.
     *
     * @param ex      A exceção inesperada capturada.
     * @param request O contexto da requisição web (adicionado para consistência).
     * @return ResponseEntity contendo uma string de erro genérica com situação 500 INTERNAL SERVER ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedErrors(Exception ex, WebRequest request) {
        log.error("Erro inesperado ocorreu: ", ex);
        String message = "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    /**
     * Handler para ResourceNotFoundException.
     * Captura a exceção ResourceNotFoundException lançada pela camada de serviço
     * quando um recurso buscado por ID não é encontrado no banco de dados.
     * Mapeia esta exceção para a situação HTTP 404 NOT FOUND.
     * <p>
     * SUMÁRIO: Trata erros de recurso não encontrado (404 NOT FOUND).
     *
     * @param ex      A exceção ResourceNotFoundException capturada, contendo a mensagem do erro.
     * @param request O contexto da requisição web.
     * @return ResponseEntity contendo a mensagem da exceção e a situação 404 NOT FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Requisição [{}]: Recurso não encontrado. Detalhes: {}", request.getDescription(false), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}