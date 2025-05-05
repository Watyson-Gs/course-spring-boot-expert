package org.project.service;

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

    public ProdutoResponse salvar(ProdutoSalvarRequest request) {
        Produto produto = salvar(mapper.toEntity(request));
        return mapper.toResponse(produto);
    }

    private Produto salvar(Produto request) {
        log.info("Iniciando processo de salvamento do produto: {}", request);

        Produto salvo = repository.save(request);

        log.info("Produto salvo com sucesso: {}", salvo);
        return salvo;
    }
}