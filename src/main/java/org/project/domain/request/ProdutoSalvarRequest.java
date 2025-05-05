package org.project.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoSalvarRequest(

        @NotBlank(message = "{campo.obrigatorio}")
        String nome,

        @NotBlank(message = "{campo.obrigatorio}")
        String descricao,

        @NotNull(message = "{campo.obrigatorio}")
        @Positive(message = "{campo.positivo}")
        BigDecimal preco
) { }