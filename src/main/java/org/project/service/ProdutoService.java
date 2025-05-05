package org.project.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;
import org.project.mapper.ProdutoMapper;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoMapper mapper;

    public ProdutoResponse salvar(ProdutoSalvarRequest request) {
        return mapper.paraResponse(save(request));
    }

    public Produto save(ProdutoSalvarRequest request) {
        return null;
    }
}