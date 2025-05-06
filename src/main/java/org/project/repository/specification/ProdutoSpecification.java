package org.project.repository.specification;

import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoBuscarRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Optional;

public class ProdutoSpecification {
    public static Specification<Produto> bySearchCriteria(ProdutoBuscarRequest request) {
        Specification<Produto> spec = Specification.where(null);
        spec = spec.and(Optional.ofNullable(request)
                .filter(req -> req.nome() != null && !req.nome().trim().isEmpty())
                .map(req -> nomeContains(req.nome()))
                .orElse(null));

        spec = spec.and(Optional.ofNullable(request)
                .filter(req -> req.descricao() != null && !req.descricao().trim().isEmpty())
                .map(req -> descricaoContains(req.descricao()))
                .orElse(null));

        spec = spec.and(Optional.ofNullable(request)
                .filter(req -> req.minPreco() != null || req.maxPreco() != null)
                .map(req -> precoBetween(req.minPreco(), req.maxPreco()))
                .orElse(null));

        return spec;
    }

    private static Specification<Produto> nomeContains(String nome) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<Produto> descricaoContains(String descricao) {
        return (root, _, builder) ->
                builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
    }

    private static Specification<Produto> precoBetween(BigDecimal minPreco, BigDecimal maxPreco) {
        return (root, _, builder) -> {
            if (minPreco != null && maxPreco != null) {
                return builder.between(root.get("preco"), minPreco, maxPreco);
            } else if (minPreco != null) {
                return builder.greaterThanOrEqualTo(root.get("preco"), minPreco);
            } else if (maxPreco != null) {
                return builder.lessThanOrEqualTo(root.get("preco"), maxPreco);
            }
            return null;
        };
    }
}