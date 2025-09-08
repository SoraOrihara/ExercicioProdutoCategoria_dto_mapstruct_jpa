package br.com.springEstudo.ProdutoCategoria.business.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryResponseDto;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

	@Mapping(target="produtos",ignore=true)
	CategoryEntity paraCategoriaEntity(CategoryRequestDto request);
	
	CategoryResponseDto paraCategoriaResponseDto(CategoryEntity entity);
	
	List<CategoryResponseDto> paraListaCategoriaResponse(List<CategoryEntity> entities);
	
	@Mapping(target="produtos",ignore=true)
	void updateCategory(CategoryRequestDto request, @MappingTarget CategoryEntity entity);
}
