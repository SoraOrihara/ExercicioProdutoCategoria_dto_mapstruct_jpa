package br.com.springEstudo.ProdutoCategoria.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.CategoryResponseDto;
import br.com.springEstudo.ProdutoCategoria.business.mapstruct.CategoriaMapper;
import br.com.springEstudo.ProdutoCategoria.exceptions.ResourceNotFoundException;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.ProductEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.CategoryRepository;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.ProductRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final CategoriaMapper categoriaMapper;
	
	public CategoryService(CategoryRepository categoryRepository, CategoriaMapper categoriaMapper,ProductRepository productRepository) {
		this.categoriaMapper=categoriaMapper;
		this.categoryRepository=categoryRepository;
		this.productRepository=productRepository;
	}
	
	public CategoryResponseDto create(CategoryRequestDto request) {
		List<ProductEntity> produtos= new ArrayList<>();
		if(!request.productIds().isEmpty()) {
		produtos = productRepository.findAllById(request.productIds());
	}
		CategoryEntity category = categoriaMapper.paraCategoriaEntity(request);
		category.setProdutos(produtos.stream().collect(Collectors.toSet()));
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
		
		if (request.productIds() != null && !request.productIds().isEmpty()) {
	        Set<ProductEntity> newProducts = productRepository.findAllById(request.productIds())
	            .stream()
	            .collect(Collectors.toSet());

	        // 5. Add the newly fetched product entities to the category
	        category.setProdutos(newProducts);
	    }
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
