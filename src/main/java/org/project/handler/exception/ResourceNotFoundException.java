package org.project.handler.exception;

/**
 * Exceção customizada que indica que um recurso solicitado (por exemplo, uma entidade por ID)
 * não foi encontrado no sistema ou na fonte de dados (ex: banco de dados).
 * <p>
 * Esta exceção é uma {@link RuntimeException} e é tipicamente lançada pela camada de serviço
 * quando uma operação de busca (como {@code findById}) não retorna um resultado para um
 * identificador esperado. Ela serve como um sinal semântico claro.
 * <p>
 * Um handler de exceções apropriado (como em um {@link org.springframework.web.bind.annotation.RestControllerAdvice GlobalExceptionHandler})
 * deve capturar esta exceção e mapeá-la para uma resposta HTTP com status {@code 404 NOT FOUND},
 * informando ao cliente da API que o recurso requisitado não existe.
 *
 * @see org.project.handler.GlobalExceptionHandler
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que cria uma nova exceção ResourceNotFoundException com uma mensagem detalhada.
     * A mensagem deve ser informativa sobre qual recurso não foi encontrado (ex: "Produto não encontrado com ID: 123").
     *
     * @param message A mensagem detalhada do erro.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor que cria uma nova exceção ResourceNotFoundException com uma mensagem
     * detalhada e a causa subjacente.
     * Use este construtor quando a ausência do recurso for resultado direto de outra exceção.
     *
     * @param message A mensagem detalhada do erro.
     * @param cause   A causa subjacente da exceção (salva para recuperação posterior).
     * @see ResourceNotFoundException#ResourceNotFoundException(String)
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}