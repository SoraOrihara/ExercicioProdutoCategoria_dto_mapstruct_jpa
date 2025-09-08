package br.com.springEstudo.ProdutoCategoria.business.dto;


import java.util.Set;
import java.util.UUID;

public record ProdutoResponseDto(UUID id, String nome, Double preco, Integer quantidade, Set<CategoryReferenceDto> categorias) {

}
