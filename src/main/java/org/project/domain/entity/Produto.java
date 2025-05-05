package org.project.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "{campo.obrigatorio}")
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "{campo.obrigatorio}")
    private String descricao;

    @Column(nullable = false, precision = 16, scale = 4)
    @NotNull(message = "{campo.obrigatorio}")
    @Positive(message = "{campo.positivo}")
    private BigDecimal preco;
}