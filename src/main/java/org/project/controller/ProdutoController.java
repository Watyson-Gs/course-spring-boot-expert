package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("produto")
public class ProdutoController {

    private final ProdutoService service;

    @Operation(
            summary = "Cadastrar novo produto",
            description = """
                    Criação de um produto no sistema.
                    
                    Campos obrigatórios:
                    - nome: deve conter texto válido (não vazio ou apenas espaços);
                    - descrição: deve conter texto válido (não vazio ou apenas espaços);
                    - preço: deve ser um valor numérico positivo maior que zero.
                    
                    Retorna os dados do produto criado em caso de sucesso.
                    """
    )
    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoSalvarRequest request) {
        ProdutoResponse response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}