package org.project.domain.object.carro;

import org.project.domain.enums.ModeloCarro;
import org.project.domain.enums.Montadora;
import org.project.domain.object.Carro;
import org.project.domain.object.Motor;

import java.awt.*;

public class HondaHRV extends Carro {
    public HondaHRV(Motor motor) {
        super(motor);
        setModelo(ModeloCarro.HVR);
        setCor(Color.BLACK);
        setMontadora(Montadora.HONDA);
    }
}