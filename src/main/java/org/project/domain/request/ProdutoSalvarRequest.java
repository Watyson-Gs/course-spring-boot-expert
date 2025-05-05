package org.project.domain.request;

import java.math.BigDecimal;

public record ProdutoSalvarRequest(
        String nome,
        String descricao,
        BigDecimal preco
) { }