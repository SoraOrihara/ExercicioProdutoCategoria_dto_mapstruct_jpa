package br.com.springEstudo.ProdutoCategoria.seeder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.CategoryEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.entities.ProductEntity;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.CategoryRepository;
import br.com.springEstudo.ProdutoCategoria.infraestructure.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    	  // Limpa o banco de dados antes de popular
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();

        // Dados estáticos para evitar erros
        List<String> categoryNames = Arrays.asList(
            "Eletrônicos", "Casa e Jardim", "Ferramentas", "Moda", "Esportes"
        );

        List<String> productNames = Arrays.asList(
            "Smartphone", "Notebook", "Televisão 4K", "Fone de Ouvido Bluetooth",
            "Cadeira de Escritório", "Mesa de Jantar", "Sofá 3 Lugares",
            "Furadeira Elétrica", "Kit de Chaves de Fenda", "Serra Elétrica",
            "Camiseta", "Calça Jeans", "Jaqueta de Couro", "Tênis Casual",
            "Bola de Futebol", "Halteres", "Bicicleta", "Tapete de Yoga"
        );
        
        Random random = new Random();

        // 1. Cria e salva as categorias
        List<CategoryEntity> categories = categoryNames.stream()
            .map(name -> {
                CategoryEntity category = new CategoryEntity();
                category.setNome(name);
                return category;
            })
            .collect(Collectors.toList());

        categoryRepository.saveAll(categories);

        // 2. Cria os produtos e os associa aleatoriamente às categorias
        List<ProductEntity> products = productNames.stream()
            .map(name -> {
                ProductEntity product = new ProductEntity();
                product.setNome(name);
                // Preços e quantidades aleatórios, mas sem erro de formatação
                product.setPreco(10.0 + random.nextDouble() * 500.0);
                product.setQuantidade(random.nextInt(100) + 1);
    
                // Associa o produto a um número aleatório de categorias (1 a 3)
                Set<CategoryEntity> associatedCategories = new HashSet<>();
                int numberOfCategories = random.nextInt(3) + 1;
                
                IntStream.range(0, numberOfCategories).forEach(j -> {
                    int randomIndex = random.nextInt(categories.size());
                    associatedCategories.add(categories.get(randomIndex));
                });

                product.setCategorias(associatedCategories);
                
                // Sincroniza o lado inverso do relacionamento
                associatedCategories.forEach(category -> category.getProdutos().add(product));

                return product;
            })
            .collect(Collectors.toList());

        // 3. Salva todos os produtos, o que também salva os relacionamentos
        productRepository.saveAll(products);

        System.out.println("Base de dados populada com sucesso!");
    }
}
