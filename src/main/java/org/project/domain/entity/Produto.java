package org.project.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "{validacao.campo.obrigatorio}")
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "{validacao.campo.obrigatorio}")
    private String descricao;

    @Column(nullable = false, precision = 16, scale = 4)
    @NotNull(message = "{validacao.campo.obrigatorio}")
    @Positive(message = "{validacao.campo.positivo}")
    private BigDecimal preco;
}