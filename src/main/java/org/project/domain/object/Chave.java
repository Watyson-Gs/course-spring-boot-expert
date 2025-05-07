package org.project.domain.object;

import lombok.*;
import org.project.domain.enums.Montadora;
import org.project.domain.enums.TipoChave;

@Getter
@Setter
@ToString
public class Chave {
    private Montadora montadora;
    private TipoChave tipo;
}