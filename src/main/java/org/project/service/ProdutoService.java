package org.project.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.mapper.ProdutoMapper;
import org.project.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

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
}