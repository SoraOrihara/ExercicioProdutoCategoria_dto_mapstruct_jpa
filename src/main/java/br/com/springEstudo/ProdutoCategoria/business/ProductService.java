package br.com.springEstudo.ProdutoCategoria.business;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoResponseDto;
import br.com.springEstudo.ProdutoCategoria.business.mapstruct.ProdutoMapper;
import br.com.springEstudo.ProdutoCategoria.exceptions.ResourceNotFoundException;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.ProductEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.CategoryRepository;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProdutoMapper productMapper;

	@Transactional
	public ProdutoResponseDto createProduct(ProdutoRequestDto requestDto) {
		// 1. Mapear o DTO de requisição para a entidade, ignorando a lista de
		// categorias.
		ProductEntity product = productMapper.paraProdutoEntity(requestDto);

		// 2. Buscar as entidades de categoria com base nos IDs do DTO de requisição.
		if (requestDto.categoryId() != null && !requestDto.categoryId().isEmpty()) {
			Set<CategoryEntity> categories = categoryRepository.findAllById(requestDto.categoryId()).stream()
					.collect(Collectors.toSet());

			// Lançar exceção se alguma categoria não for encontrada.
			if (categories.size() != requestDto.categoryId().size()) {
				throw new ResourceNotFoundException("Uma ou mais categorias não foram encontradas.");
			}

			// 3. Estabelecer o relacionamento bidirecional.
			product.setCategorias(categories);
			categories.forEach(c -> c.getProdutos().add(product));
		}

		// 4. Salvar a entidade e o relacionamento.
		ProductEntity savedProduct = productRepository.save(product);

		// 5. Mapear a entidade salva para o DTO de resposta e retornar.
		return productMapper.paraProdutoResponseDto(savedProduct);
	}

	@Transactional
	public List<ProdutoResponseDto> getAllProducts() {
		return productMapper.paraListaResponseDto(productRepository.findAll());
	}
	@Transactional
	public ProdutoResponseDto getProductById(UUID id) {
		ProductEntity product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
		return productMapper.paraProdutoResponseDto(product);
	}

	@Transactional
	public ProdutoResponseDto updateProduct(UUID id, ProdutoRequestDto requestDto) {
		// 1. Encontrar o produto existente.
		ProductEntity existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

		// 2. Usar o mapper para atualizar os campos básicos.
		productMapper.updateProduto(requestDto, existingProduct);

		// 3. Sincronizar a relação ManyToMany com as categorias.
		if (requestDto.categoryId() != null) {
			Set<CategoryEntity> newCategories = categoryRepository.findAllById(requestDto.categoryId()).stream()
					.collect(Collectors.toSet());

			if (newCategories.size() != requestDto.categoryId().size()) {
				throw new ResourceNotFoundException("Uma ou mais categorias não foram encontradas.");
			}

			// Limpa as associações antigas e adiciona as novas.
			existingProduct.getCategorias().clear();
			existingProduct.getCategorias().addAll(newCategories);
		}

		// 4. Salvar as mudanças.
		ProductEntity updatedProduct = productRepository.save(existingProduct);

		// 5. Mapear e retornar.
		return productMapper.paraProdutoResponseDto(updatedProduct);
	}

	@Transactional
	public void deleteProduct(UUID id) {
		ProductEntity product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

		// Remove as associações antes de deletar o produto
		product.getCategorias().forEach(category -> category.getProdutos().remove(product));
		product.getCategorias().clear();

		productRepository.delete(product);
	}
}
