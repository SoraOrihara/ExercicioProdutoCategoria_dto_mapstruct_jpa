package br.com.springEstudo.ProdutoCategoria.business.dto;





import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank(message="O nome é obrigatorio") String nome) {

}
