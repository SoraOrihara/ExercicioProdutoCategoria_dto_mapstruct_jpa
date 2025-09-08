package br.com.springEstudo.ProdutoCategoria.business;


import java.util.List;

import java.util.UUID;


import org.springframework.stereotype.Service;

import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryResponseDto;
import br.com.springEstudo.ProdutoCategoria.business.mapstruct.CategoriaMapper;
import br.com.springEstudo.ProdutoCategoria.exceptions.ResourceNotFoundException;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.CategoryRepository;


@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	private final CategoriaMapper categoriaMapper;
	
	public CategoryService(CategoryRepository categoryRepository, CategoriaMapper categoriaMapper) {
		this.categoriaMapper=categoriaMapper;
		this.categoryRepository=categoryRepository;
		
	}
	
	public CategoryResponseDto create(CategoryRequestDto request) {
		CategoryEntity category = categoriaMapper.paraCategoriaEntity(request);
		CategoryEntity categoriaSalva = categoryRepository.save(category);
		return categoriaMapper.paraCategoriaResponseDto(categoriaSalva);
	}	
	
	public List<CategoryResponseDto> findAll() {
		return categoriaMapper.paraListaCategoriaResponse(categoryRepository.findAll());
	}
	
	public CategoryResponseDto findById(UUID id) {
		return categoriaMapper.paraCategoriaResponseDto(categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado: "+id)));
	}
	
	public CategoryResponseDto update(UUID id,CategoryRequestDto request) {
		CategoryEntity category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado: "+id));
		categoriaMapper.updateCategory(request, category);
		
		category.getProdutos().clear();
		
		CategoryEntity categoriaSalva = categoryRepository.save(category);
		return categoriaMapper.paraCategoriaResponseDto(categoriaSalva);
	}
	
	public void deleteById(UUID id) {
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
		categoryRepository.deleteById(id);
	}
	
}
