package org.project.mapper;

import org.mapstruct.Mapper;
import org.project.domain.entity.Produto;
import org.project.domain.response.ProdutoResponse;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoResponse paraResponse(Produto produto);
}