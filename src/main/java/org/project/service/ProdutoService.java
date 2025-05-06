package org.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.handler.exception.ResourceNotFoundException;
import org.project.mapper.ProdutoMapper;
import org.project.repository.ProdutoRepository;
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
    public ProdutoResponse salvar(final ProdutoSalvarRequest request) {
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
    private Optional<Produto> buscarPorId(Integer id) {
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
    private Produto obterPorId(Integer id) {
        Optional<Produto> result = buscarPorId(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
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
    public ProdutoResponse obterResponsePorId(Integer id) {
        return mapper.toResponse(obterPorId(id));
    }
}