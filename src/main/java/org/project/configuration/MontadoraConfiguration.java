package org.project.configuration;

import org.project.domain.enums.ModeloCarro;
import org.project.domain.enums.TipoMotor;
import org.project.domain.object.Motor;
import org.project.util.BeanName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MontadoraConfiguration {
    @Primary
    @Bean(name = BeanName.MOTOR_TURBO)
    public Motor motorTurbo(){
        var motor = new Motor();
        motor.setCavalos(180);
        motor.setCilindros(4);
        motor.setModelo(ModeloCarro.XPTO_1);
        motor.setLitragem(1.5);
        motor.setTipo(TipoMotor.TURBO);
        return motor;
    }

    @Bean(name = BeanName.MOTOR_ELETRICO)
    public Motor motorEletrico(){
        var motor = new Motor();
        motor.setCavalos(110);
        motor.setCilindros(3);
        motor.setModelo(ModeloCarro.TH_40);
        motor.setLitragem(1.4);
        motor.setTipo(TipoMotor.ELETRICO);
        return motor;
    }

    @Bean(name = BeanName.MOTOR_ASPIRADO)
    public Motor motorAspirado(){
        var motor = new Motor();
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setModelo(ModeloCarro.XPTO_0);
        motor.setLitragem(2.0);
        motor.setTipo(TipoMotor.ASPIRADO);
        return motor;
    }
}