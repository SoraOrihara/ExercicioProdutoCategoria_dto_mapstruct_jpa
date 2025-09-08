package br.com.springEstudo.ProdutoCategoria.business.dto;



import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank(message="O nome é obrigatorio") String nome, Set<UUID> productIds) {

}
