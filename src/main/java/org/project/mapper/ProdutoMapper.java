package org.project.mapper;

import org.mapstruct.*;
import org.project.domain.entity.Produto;
import org.project.domain.request.ProdutoSalvarRequest;
import org.project.domain.response.ProdutoResponse;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProdutoMapper {
    @Mapping(target = "id", ignore = true)
    Produto toEntity(ProdutoSalvarRequest request);

    ProdutoResponse toResponse(Produto produto);
}