package org.project.util;

public final class BeanName {
    private BeanName() {
        throw new UnsupportedOperationException("Esta é uma classe de utilidade e não deve ser instanciada");
    }

    public static final String MOTOR_ASPIRADO = "motorAspirado";
    public static final String MOTOR_ELETRICO = "motorEletrico";
    public static final String MOTOR_TURBO = "motorTurbo";
}