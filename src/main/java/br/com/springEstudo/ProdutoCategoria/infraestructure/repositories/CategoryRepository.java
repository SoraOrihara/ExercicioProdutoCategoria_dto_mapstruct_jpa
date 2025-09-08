package br.com.springEstudo.ProdutoCategoria.infraestructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

}
