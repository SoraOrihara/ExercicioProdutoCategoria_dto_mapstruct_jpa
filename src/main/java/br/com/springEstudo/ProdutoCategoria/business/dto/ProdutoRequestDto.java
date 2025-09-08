package br.com.springEstudo.ProdutoCategoria.business.dto;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProdutoRequestDto(@NotBlank(message="O nome do produto é obrigatorio") String nome, 
		@Min(value=0, message="Quantidade deve ser positiva") Integer quantidade,
		@Min(value=0,message="Preço deve ser positivo") Double preco,
		Set<UUID> categoryId) {

}
