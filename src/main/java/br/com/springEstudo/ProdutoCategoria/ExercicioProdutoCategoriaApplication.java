package br.com.springEstudo.ProdutoCategoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExercicioProdutoCategoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExercicioProdutoCategoriaApplication.class, args);
	}

}
