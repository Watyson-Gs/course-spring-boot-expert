package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.project.domain.enums.CarroStatus;
import org.project.domain.object.Chave;
import org.project.service.TesteFabricaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("testes-fabrica")
@Tag(name = "Testes de Fábrica", description = "Gerenciamento de execuções de testes de fábrica")
public class TesteFabricaController {
    private final TesteFabricaService service;

    @Operation(
            summary = "Ligar Carro com Motor Aspirado",
            description = """
                    Simula a tentativa de ligar um carro utilizando o motor aspirado configurado na fábrica.

                    **Dados Obrigatórios (Request Body):**
                    Objeto `Chave` contendo:
                    * `montadora`: Montadora da chave (Enum ou String).
                    * `tipo`: Tipo da chave (Enum, ex: TRADICIONAL, SMART).

                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna o objeto `CarroStatus` resultante da tentativa de ignição,
                    incluindo o status e a descrição da operação.
                    """
    )
    @PostMapping("/aspirado")
    public ResponseEntity<CarroStatus> ligarCarroAspirado(@RequestBody final Chave chave) {
        CarroStatus response = service.ligarCarroComMotorAspirado(chave);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Ligar Carro com Motor Aspirado",
            description = """
                    Simula a tentativa de ligar um carro utilizando o motor aspirado configurado na fábrica.

                    **Dados Obrigatórios (Request Body):**
                    Objeto `Chave` contendo:
                    * `montadora`: Montadora da chave (Enum ou String).
                    * `tipo`: Tipo da chave (Enum, ex: TRADICIONAL, SMART).

                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna o objeto `CarroStatus` resultante da tentativa de ignição,
                    incluindo o status e a descrição da operação.
                    """
    )
    @PostMapping("/eletrico")
    public ResponseEntity<CarroStatus> ligarCarroEletrico(@RequestBody final Chave chave) {
        CarroStatus response = service.ligarCarroComMotorEletrico(chave);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Ligar Carro com Motor Turbo",
            description = """
                    Simula a tentativa de ligar um carro utilizando o motor turbo configurado na fábrica.

                    **Dados Obrigatórios (Request Body):**
                    Objeto `Chave` contendo:
                    * `montadora`: Montadora da chave (Enum ou String).
                    * `tipo`: Tipo da chave (Enum, ex: TRADICIONAL, SMART).

                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna o objeto `CarroStatus` resultante da tentativa de ignição,
                    incluindo o status e a descrição da operação.
                    """
    )
    @PostMapping("/turbo")
    public ResponseEntity<CarroStatus> ligarCarroTurbo(@RequestBody final Chave chave) {
        CarroStatus response = service.ligarCarroComMotorTurbo(chave);
        return ResponseEntity.ok(response);
    }
}