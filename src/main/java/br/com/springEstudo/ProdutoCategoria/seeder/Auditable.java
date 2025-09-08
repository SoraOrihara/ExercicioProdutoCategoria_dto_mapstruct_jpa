package br.com.springEstudo.ProdutoCategoria.seeder;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
	@EntityListeners(AuditingEntityListener.class)
	public abstract class Auditable {
	    @CreatedDate
	    private Instant createdAt;

	    // Você pode adicionar um campo para a data da última atualização
	    // @LastModifiedDate
	    // private Instant updatedAt;

	    public Instant getCreatedAt() {
	        return createdAt;
	    }
	}

