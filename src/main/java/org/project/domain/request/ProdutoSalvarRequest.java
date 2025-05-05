package org.project.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoSalvarRequest(
        @NotBlank(message = "{validacao.campo.obrigatorio}")
        String nome,

        @NotBlank(message = "{validacao.campo.obrigatorio}")
        String descricao,

        @NotNull(message = "{validacao.campo.obrigatorio}")
        @Positive(message = "{validacao.campo.positivo}")
        BigDecimal preco
) { }