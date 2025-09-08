package br.com.springEstudo.ProdutoCategoria.infraestructure.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_category")
public class CategoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	private String nome;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToMany(mappedBy = "categorias",fetch=FetchType.LAZY)
	private Set<ProductEntity> produtos=new HashSet<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<ProductEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProductEntity> produtos) {
		this.produtos = produtos;
	}

	public UUID getId() {
		return id;
	}

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(String nome, Set<ProductEntity> produtos) {
		super();
		this.nome = nome;
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryEntity other = (CategoryEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
	
	
	
	
}
