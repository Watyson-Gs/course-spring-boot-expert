package org.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.annotation.MotorAspirado;
import org.project.annotation.MotorEletrico;
import org.project.annotation.MotorTurbo;
import org.project.domain.enums.CarroStatus;
import org.project.domain.object.Chave;
import org.project.domain.object.Motor;
import org.project.domain.object.carro.HondaHRV;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class TesteFabricaService {
    @MotorAspirado
    private final Motor motorAspirado;

    @MotorEletrico
    private final Motor motorEletrico;

    @MotorTurbo
    private final Motor motorTurbo;

    /**
     * Tenta ligar um carro usando o motor aspirado injetado.
     * @param chave A chave a ser usada.
     * @return A situação resultante da tentativa de ignição.
     */
    public CarroStatus ligarCarroComMotorAspirado(final Chave chave) {
        log.info("Iniciando tentativa de ligar o carro com o motor aspirado e a chave: {}", chave);
        log.debug("Criando instância de HondaHRV com o motor aspirado: {}", motorAspirado);

        CarroStatus status = new HondaHRV(motorAspirado).ignicao(chave);

        log.info("Processo de ignição (Motor Aspirado) concluído. Status: {} - Descrição: {}", status, status.getDescricao());
        return status;
    }

    /**
     * Tenta ligar um carro usando o motor elétrico injetado.
     * @param chave A chave a ser usada.
     * @return A situação resultante da tentativa de ignição.
     */
    public CarroStatus ligarCarroComMotorEletrico(final Chave chave) {
        log.info("Iniciando tentativa de ligar o carro com o motor elétrico e a chave: {}", chave);
        log.debug("Criando instância de HondaHRV com o motor elétrico: {}", motorEletrico);

        CarroStatus status = new HondaHRV(motorEletrico).ignicao(chave);

        log.info("Processo de ignição (Motor Elétrico) concluído. Status: {} - Descrição: {}", status, status.getDescricao());
        return status;
    }

    /**
     * Tenta ligar um carro usando o motor turbo injetado.
     * @param chave A chave a ser usada.
     * @return A situação resultante da tentativa de ignição.
     */
    public CarroStatus ligarCarroComMotorTurbo(final Chave chave) {
        log.info("Iniciando tentativa de ligar o carro com o motor turbo e a chave: {}", chave);
        log.debug("Criando instância de HondaHRV com o motor turbo: {}", motorTurbo);

        CarroStatus status = new HondaHRV(motorTurbo).ignicao(chave);

        log.info("Processo de ignição (Motor Turbo) concluído. Status: {} - Descrição: {}", status, status.getDescricao());
        return status;
    }
}