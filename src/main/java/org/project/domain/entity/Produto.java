package org.project.domain.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Produto {
    private String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}