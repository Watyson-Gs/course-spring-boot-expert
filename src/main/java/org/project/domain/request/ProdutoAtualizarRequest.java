package org.project.domain.request;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoAtualizarRequest(
        String nome,
        String descricao,

        @Positive(message = "{validacao.campo.positivo}")
        BigDecimal preco
) { }