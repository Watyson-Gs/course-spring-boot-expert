package org.project.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoAtualizarRequest;
import org.project.domain.request.ProdutoBuscarRequest;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.handler.exception.ResourceNotFoundException;
import org.project.domain.mapper.ProdutoMapper;
import org.project.repository.ProdutoRepository;
import org.project.repository.specification.ProdutoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    /**
     * Salva um novo produto no sistema a partir dos dados de requisição.
     * <p>
     * Esta função realiza o mapeamento do DTO de requisição ({@link ProdutoSalvarRequest}) para
     * a entidade {@link Produto}, persiste a entidade no banco de dados usando o
     * {@link ProdutoRepository#save(Object)}, e então mapeia a entidade salva de volta para
     * um DTO de resposta ({@link ProdutoResponse}).
     * <p>
     * SUMÁRIO: Cria um novo produto persistindo-o no banco de dados.
     *
     * @param request DTO contendo os dados do novo produto a ser salvo.
     * Não deve conter o ID, pois ele é gerado automaticamente.
     * @return DTO de resposta contendo os dados do produto recém-criado, incluindo o ID gerado pelo banco.
     * @see ProdutoSalvarRequest
     * @see ProdutoResponse
     */
    @Transactional
    public ProdutoResponse criar(@NonNull final ProdutoSalvarRequest request) {
        log.info("Iniciando o processo de criação de um novo produto.");
        log.debug("Dados de requisição recebidos para salvar produto: {}", request);

        Produto produtoToSave = mapper.toEntity(request);
        Produto savedProduto = repository.save(produtoToSave);

        log.info("Produto salvo com sucesso no banco de dados. ID gerado: {}", savedProduto.getId());
        log.debug("Detalhes completos da entidade Produto salva: {}", savedProduto);

        return mapper.toResponse(savedProduto);
    }

    /**
     * Função interna que realiza a busca raw de um produto pelo seu ID diretamente no repositório.
     * Inclui logs detalhados sobre o início e o resultado da interação direta com o repositório.
     * Retorna um Optional contendo a entidade Produto, se encontrada.
     * Não lida com o caso de "não encontrado" lançando exceção, apenas retorna Optional vazio.
     * <p>
     * SUMÁRIO: Busca Optional de Produto por ID (uso interno, logs detalhados da busca raw).
     *
     * @param id O ID do produto a ser buscado.
     * @return Optional contendo a entidade Produto, ou Optional.empty() se não encontrada.
     */
    @Transactional(readOnly = true)
    private Optional<Produto> buscarPorId(@NonNull final Integer id) {
        log.debug("Iniciando busca no repositório por ID: {}", id);
        Optional<Produto> result = repository.findById(id);
        log.debug("Fim da busca no repositório por ID {}. Resultado presente: {}", id, result.isPresent());
        return result;
    }

    /**
     * Função interna que busca um produto pelo seu ID e retorna a entidade Produto.
     * Utiliza {@code buscarPorId} para a busca raw e valida se o resultado é presente.
     * Lança {@code ResourceNotFoundException} se não encontrar.
     * É a fonte validada da entidade Produto dentro do serviço.
     * <p>
     * SUMÁRIO: Obtém entidade, Produto por ID (uso interno, valida e lança 404 se nao encontrar).
     *
     * @param id O ID do produto a ser buscado.
     * @return A entidade Produto encontrada (nunca null se não lançar exceção).
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID especificado.
     */
    private Produto obterPorId(final Integer id) {
        return buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    /**
     * Busca um produto pelo seu ID e retorna um DTO de resposta.
     * Inicia o fluxo público para obter um produto. Delega a busca raw
     * e a validação/obtenção da entidade para métodos privados.
     * Lança ResourceNotFoundException se o produto não for encontrado.
     * <p>
     * SUMÁRIO: Obtém um produto (DTO) por ID para a camada de apresentação/API.
     *
     * @param id O ID do produto a ser buscado.
     * @return DTO de resposta com os dados do produto encontrado.
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID especificado.
     */
    public ProdutoResponse obterResponsePorId(final Integer id) {
        return mapper.toResponse(obterPorId(id));
    }

    /**
     * Busca produtos com base nos critérios fornecidos no DTO de busca, com suporte a paginação e ordenação.
     * Utiliza Spring Data JPA Specifications para construir a consulta dinamicamente.
     * Retorna uma página de DTOs de resposta.
     * <p>
     * SUMÁRIO: Busca produtos (DTOs) por critérios de filtro, com paginação e ordenação.
     *
     * @param request DTO contendo os critérios de busca (nome, descricao, minPreco, maxPreco).
     * Pode ser nulo se nenhum critério de busca for fornecido.
     * @param pageable Objeto Pageable contendo informações de paginação e ordenação. Não deve ser nulo.
     * @return Uma página de DTOs {@link ProdutoResponse} contendo os produtos que correspondem aos critérios.
     * Retorna uma página vazia se nenhum produto for encontrado.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoResponse> buscar(final ProdutoBuscarRequest request, @NonNull final Pageable pageable) {
        log.info("Iniciando busca paginada de produtos");
        log.debug("Critérios de busca recebidos: {}", request);
        log.debug("Informações de paginação e ordenação: {}", pageable);

        Specification<Produto> spec = ProdutoSpecification.bySearchCriteria(request);
        Page<Produto> produtosPage = repository.findAll(spec, pageable);

        log.info("Busca paginada de produtos finalizada. Página {} de {}, Total de elementos: {}",
                produtosPage.getNumber() + 1, produtosPage.getTotalPages(), produtosPage.getTotalElements());

        return produtosPage.map(mapper::toResponse);
    }

    /**
     * Atualiza os dados de um produto existente.
     * Busca o produto pelo ID. Se encontrado, atualiza seus campos com base nos dados
     * fornecidos no DTO de requisição (apenas campos não nulos). Salva as alterações
     * e retorna o produto atualizado como DTO de resposta.
     * <p>
     * SUMÁRIO: Atualiza um produto existente por ID.
     *
     * @param id O ID do produto a ser atualizado.
     * @param request DTO contendo os dados para atualização. Apenas campos não nulos serão considerados.
     * @return DTO de resposta com os dados do produto atualizado.
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID especificado.
     */
    @Transactional
    private Produto atualizar(@NonNull final Integer id, @NonNull final ProdutoAtualizarRequest request) {
        log.info("Iniciando o processo de atualização do produto com ID: {}", id);
        log.debug("Dados de requisição recebidos para atualizar produto com ID {}: {}", id, request);

        Produto produtoToUpdate = obterPorId(id);

        log.debug("Produto existente encontrado para atualização: {}", produtoToUpdate);

        mapper.toRequest(request, produtoToUpdate);
        Produto updatedProduto = repository.save(produtoToUpdate);

        log.info("Produto com ID {} atualizado com sucesso.", updatedProduto.getId());
        log.debug("Detalhes completos da entidade Produto atualizada: {}", updatedProduto);

        return updatedProduto;
    }

    /**
     * Atualiza os dados de um produto existente e retorna um DTO de resposta.
     * Utiliza a função {@code atualizar} para realizar a lógica de atualização
     * e então mapeia a entidade atualizada para um DTO de resposta.
     * Esta é a função para a camada de apresentação/API realizar atualizações.
     * <p>
     * SUMÁRIO: Atualiza um produto existente por ID (uso público, retorna DTO).
     *
     * @param id O ID do produto a ser atualizado.
     * @param request DTO contendo os dados para atualização. Apenas campos não nulos serão considerados.
     * @return DTO de resposta com os dados do produto atualizado.
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID especificado.
     */
    public ProdutoResponse atualizarResponse(final Integer id, final ProdutoAtualizarRequest request) {
        return mapper.toResponse(atualizar(id, request));
    }

    /**
     * Deleta um produto pelo seu ID.
     * Primeiro verifica se o produto existe usando {@code obterPorId}.
     * Se o produto não for encontrado, {@code obterPorId} lançará {@code ResourceNotFoundException}.
     * Se o produto for encontrado, ele é deletado do repositório.
     * <p>
     * SUMÁRIO: Deleta um produto por ID, verificando sua existência primeiro.
     *
     * @param id O ID do produto a ser deletado.
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID especificado.
     */
    @Transactional
    public void deletar(@NonNull final Integer id) {
        log.info("Iniciando exclusão do produto com ID: {}", id);

        Produto produto = obterPorId(id);
        repository.deleteById(produto.getId());

        log.info("Produto com ID {} excluído com sucesso.", produto.getId());
    }
}