package br.com.springEstudo.ProdutoCategoria.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springEstudo.ProdutoCategoria.business.ProductService;
import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoRequestDto;
import br.com.springEstudo.ProdutoCategoria.business.dto.ProdutoResponseDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	/**
	 * Cria um novo produto no inventário.
	 * 
	 * @param requestDTO O DTO de requisição do produto.
	 * @return O DTO de resposta do produto criado com status 201 (Created).
	 */
	@PostMapping
	public ResponseEntity<ProdutoResponseDto> createProduct(@Valid @RequestBody ProdutoRequestDto requestDto) {
		ProdutoResponseDto responseDto = productService.createProduct(requestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	/**
	 * Lista todos os produtos do inventário.
	 * 
	 * @return Uma lista de DTOs de resposta de produtos com status 200 (OK).
	 */
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDto>> getAllProducts() {
		List<ProdutoResponseDto> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	/**
	 * Busca um produto por seu ID.
	 * 
	 * @param id O UUID do produto.
	 * @return O DTO de resposta do produto encontrado com status 200 (OK).
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDto> getProductById(@PathVariable UUID id) {
		ProdutoResponseDto product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	/**
	 * Atualiza um produto existente.
	 * 
	 * @param id         O UUID do produto a ser atualizado.
	 * @param requestDTO O DTO de requisição com os dados atualizados.
	 * @return O DTO de resposta do produto atualizado com status 200 (OK).
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDto> updateProduct(@PathVariable UUID id,
			@Valid @RequestBody ProdutoRequestDto requestDto) {
		ProdutoResponseDto updatedProduct = productService.updateProduct(id, requestDto);
		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * Exclui um produto por seu ID.
	 * 
	 * @param id O UUID do produto a ser excluído.
	 * @return Resposta sem conteúdo com status 204 (No Content).
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
