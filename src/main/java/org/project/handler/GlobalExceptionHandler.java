package org.project.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Esta classe é responsável por tratar exceções globais que ocorrem em toda a aplicação, especialmente erros de validação de entrada.
 * Através de {@link RestControllerAdvice}, ela captura exceções de validação e formata as mensagens de erro para uma resposta apropriada.
 * <p>
 * Utiliza a anotação {@link Slf4j} para gerar um logger, o que permite registrar detalhes sobre os erros durante o processamento das exceções.
 *
 * @see MethodArgumentNotValidException
 * @see ResponseEntity
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura todas as exceções inesperadas que não foram tratadas por handlers específicos e retorna uma resposta genérica de erro.
     * <p>
     * Isso impede que erros internos não tratados ou inesperados sejam expostos ao usuário final,
     * proporcionando uma mensagem amigável e registrando o erro completo nos logs para análise.
     *
     * @param ex A exceção genérica inesperada.
     * @return Uma resposta com situação HTTP 500 (Internal Server Error) e uma mensagem genérica de erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedErrors(Exception ex) {
        // Registra o erro completo no log para análise interna
        log.error("Erro inesperado ocorreu: ", ex);

        // Retorna uma mensagem genérica para o usuário
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
    }

    /**
     * Captura exceções de validação e retorna uma resposta personalizada com detalhes dos erros de campo.
     * Esta função é acionada quando uma exceção do tipo {@link MethodArgumentNotValidException} é lançada,
     * ou seja, quando um dos campos de entrada do request não está conforme as regras de validação definidas, como @NotBlank, @NotNull, etc.
     * <p>
     * A resposta contém as mensagens de erro para os campos que falharam na validação, além do situação HTTP 400 (Bad Request).
     *
     * @param ex A exceção {@link MethodArgumentNotValidException} contendo detalhes sobre os erros de validação dos campos.
     * @return Uma resposta com situação HTTP 400 (Bad Request) e as mensagens de erro dos campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();

            errorMessage.append(String.format("Campo '%s': %s. ", fieldName, message));
        });

        if (errorMessage.isEmpty()) {
            errorMessage.append("Erro de validação desconhecido.");
        }

        log.error(String.valueOf(errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }
}