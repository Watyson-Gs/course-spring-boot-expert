package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("produtos")
@Tag(name = "Produto", description = "Gerenciamento de Produtos")
public class ProdutoController {

    private final ProdutoService service;

    @Operation(
            summary = "Criar Novo Produto",
            description = """
                    Cria um novo registro de produto no sistema.
                    
                    **Dados Obrigatórios (Request Body):**
                    * `nome`: Nome do produto (texto, não pode ser vazio).
                    * `descricao`: Descrição detalhada do produto (texto, não pode ser vazia).
                    * `preco`: Preço unitário do produto (valor numérico positivo, maior que zero).
                    
                    **Resposta de Sucesso (HTTP 201 Created):**
                    Retorna os detalhes completos do produto recém-criado, incluindo seu ID gerado.
                    """
    )
    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoSalvarRequest request) {
        ProdutoResponse response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}