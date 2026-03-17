package br.com.fatec.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity // Diz ao JPA que a classe é uma tabela
@Data   // Lombok: gera getters/setters/etc
public class Produto {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // "IDENTITY" Auto-incremento
    private Long id;

    @NotBlank(message = "O nome é obrigatório e não pode conter apenas espaços em branco")
    @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres")
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser um valor positivo maior que zero")
    private Double preco;
}