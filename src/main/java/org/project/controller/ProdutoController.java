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
import org.springframework.web.bind.annotation.*;

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
        ProdutoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Obter Produto por ID",
            description = """
                    Busca e retorna os detalhes de um produto específico utilizando seu ID.
                    
                    **Parâmetros (Path Variable):**
                    * `{id}`: O ID do produto a ser buscado (valor inteiro).
                    
                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna os dados completos do produto encontrado.
                    
                    **Resposta de Erro (HTTP 404 Not Found):**
                    Retorna uma mensagem indicando que o produto com o ID fornecido não foi encontrado.
                    """
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> obterPorId(@PathVariable Integer id) {
        ProdutoResponse response = service.obterResponsePorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deletar Produto por ID",
            description = """
                    Deleta um produto específico do sistema utilizando seu ID.
                    
                    **Parâmetros (Path Variable):**
                    * `{id}`: O ID do produto a ser deletado (valor inteiro).
                    
                    **Resposta de Sucesso (HTTP 204 No Content):**
                    Indica que o produto foi deletado com sucesso. Não há corpo na resposta.
                    
                    **Resposta de Erro (HTTP 404 Not Found):**
                    Retorna uma mensagem indicando que o produto com o ID fornecido não foi encontrado.
                    """
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}