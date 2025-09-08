package br.com.springEstudo.ProdutoCategoria.business.mapstruct;

import java.util.List;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoResponseDto;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	@Mapping(target="categorias",ignore=true)
	ProductEntity paraProdutoEntity(ProdutoRequestDto request);
	
	ProdutoResponseDto paraProdutoResponseDto(ProductEntity entity);
	
	List<ProdutoResponseDto> paraListaResponseDto(List<ProductEntity> entities);
	
	@Mapping(target="categorias",ignore=true)
	void updateProduto(ProdutoRequestDto request,@MappingTarget ProductEntity entity);
	
}
