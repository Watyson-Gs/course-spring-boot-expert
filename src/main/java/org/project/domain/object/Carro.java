package org.project.domain.object;

import lombok.*;
import org.project.domain.enums.CarroStatus;
import org.project.domain.enums.ModeloCarro;
import org.project.domain.enums.Montadora;

import java.awt.*;

@Getter
@Setter
@ToString
public class Carro {
    private ModeloCarro modelo;
    private Color cor;
    private Motor motor;
    private Montadora montadora;

    public Carro(Motor motor) {
        this.motor = motor;
    }

    public CarroStatus ignicao(Chave chave) {
        if(chave.getMontadora() != montadora) {
            return CarroStatus.FALHA_AO_LIGAR;
        }

        return CarroStatus.LIGADO;
    }
}