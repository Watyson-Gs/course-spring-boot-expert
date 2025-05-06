package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.domain.request.ProdutoAtualizarRequest;
import org.project.domain.request.ProdutoBuscarRequest;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            summary = "Buscar Produtos por Critérios (Paginado e Ordenável)",
            description = """
                    Busca produtos com base em critérios de filtro opcionais, com suporte a paginação e ordenação.
                    
                    **Parâmetros de Busca (Query Parameters):**
                    * `nome`: Filtra por nome do produto (busca parcial, case-insensitive).
                    * `descricao`: Filtra por descrição do produto (busca parcial, case-insensitive).
                    * `minPreco`: Filtra por preço mínimo (inclusive).
                    * `maxPreco`: Filtra por preço máximo (inclusive).
                    
                    **Parâmetros de Paginação e Ordenação (Query Parameters - Padrão Spring Data):**
                    * `page`: Número da página (baseado em zero, padrão é 0).
                    * `size`: Número de resultados por página (padrão é 10).
                    * `sort`: Propriedade pela qual ordenar, seguida por ',asc' ou ',desc' (ex: `sort=nome,asc`). Pode ser repetido para múltiplas propriedades.
                    
                    Você pode combinar critérios de busca com parâmetros de paginação/ordenação.
                    Se nenhum parâmetro de busca for fornecido, a busca retornará todos os produtos paginados/ordenados.
                    
                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna um objeto Page contendo a lista de DTOs de resposta para a página solicitada,
                    juntamente com metadados de paginação (total de elementos, total de páginas, etc.).
                    A lista de conteúdo pode estar vazia se nenhum produto for encontrado na página.
                    """
    )
    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> buscar(
            @ModelAttribute @Valid ProdutoBuscarRequest request,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        Page<ProdutoResponse> responseList = service.buscar(request, pageable);
        return ResponseEntity.ok(responseList);
    }

    @Operation(
            summary = "Atualizar Produto por ID",
            description = """
                    Atualiza os dados de um produto existente utilizando seu ID.
                    Permite atualização parcial: apenas os campos fornecidos no corpo da requisição serão atualizados.
                    
                    **Parâmetros (Path Variable):**
                    * `{id}`: O ID do produto a ser atualizado (valor inteiro).
                    
                    **Dados Opcionais (Request Body):**
                    * `nome`: Novo nome do produto (texto).
                    * `descricao`: Nova descrição do produto (texto).
                    * `preco`: Novo preço unitário do produto (valor numérico positivo).
                    
                    **Resposta de Sucesso (HTTP 200 OK):**
                    Retorna os dados completos do produto atualizado.
                    
                    **Resposta de Erro (HTTP 400 Bad Request):**
                    Retorna erros de validação se os dados fornecidos forem inválidos (ex: preço negativo).
                    
                    **Resposta de Erro (HTTP 404 Not Found):**
                    Retorna uma mensagem indicando que o produto com o ID fornecido não foi encontrado.
                    """
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Integer id, @RequestBody @Valid ProdutoAtualizarRequest request
    ) {
        ProdutoResponse response = service.atualizarResponse(id, request);
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