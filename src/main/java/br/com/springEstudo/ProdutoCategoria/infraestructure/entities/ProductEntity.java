package br.com.springEstudo.ProdutoCategoria.infraestructure.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(name="nome",unique=true,nullable=false)
	private String nome;
	
	@Column(name="quantidade",nullable = false)
	private Integer quantidade;
	
	@Column(name="preco",nullable = false)
	private Double preco;
	
	@CreatedDate
	private Instant criadoEm;
	
	@ManyToMany
	@JoinTable(
			name="tb_product_category",
			joinColumns = @JoinColumn(name="product_id"),
			inverseJoinColumns = @JoinColumn(name="category_id")
			)
	private Set<CategoryEntity> categorias = new HashSet<>();


	
	public ProductEntity() {
		super();
	}



	public ProductEntity(String nome, Integer quantidade, Double preco, Set<CategoryEntity> categorias) {
		super();
		this.nome = nome;
		this.quantidade = quantidade;
		this.preco = preco;
		this.categorias = categorias;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public Integer getQuantidade() {
		return quantidade;
	}



	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}



	public Double getPreco() {
		return preco;
	}



	public void setPreco(Double preco) {
		this.preco = preco;
	}



	public Set<CategoryEntity> getCategorias() {
		return categorias;
	}



	public void setCategorias(Set<CategoryEntity> categorias) {
		this.categorias = categorias;
	}



	public UUID getId() {
		return id;
	}



	@Override
	public int hashCode() {
		return Objects.hash(categorias, id, nome);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductEntity other = (ProductEntity) obj;
		return Objects.equals(categorias, other.categorias) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
}
