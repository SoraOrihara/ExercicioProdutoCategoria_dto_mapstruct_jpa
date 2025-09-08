package br.com.springEstudo.ProdutoCategoria.business.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CategoryResponseDto(UUID id, String nome, Set<ProductReferenceDto>produtos, Instant criadoEm) {

}
