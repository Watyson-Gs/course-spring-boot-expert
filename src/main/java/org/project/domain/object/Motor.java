package org.project.domain.object;

import lombok.*;
import org.project.domain.enums.ModeloCarro;
import org.project.domain.enums.TipoMotor;

@Getter
@Setter
@ToString
public class Motor {
    private ModeloCarro modelo;
    private Integer cavalos;
    private Integer cilindros;
    private Double litragem;
    private TipoMotor tipo;
}