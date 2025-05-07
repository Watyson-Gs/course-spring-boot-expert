package org.project.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarroStatus {
    LIGADO("Carro ligado com sucesso"),
    FALHA_AO_LIGAR("Não foi possível ligar o carro");

    private final String descricao;
}