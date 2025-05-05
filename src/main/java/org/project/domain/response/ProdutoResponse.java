package org.project.domain.response;

import java.math.BigDecimal;

public record ProdutoResponse(
        Integer id,
        String nome,
        String descricao,
        BigDecimal preco
) { }