package br.com.fatec.api.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // 🔥 NOVO: relacionamento com Produto
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Produto> produtos;

    // Construtor vazio (obrigatório para JPA)
    public Categoria() {
    }

    // Construtor com parâmetros (opcional, mas útil)
    public Categoria(String nome) {
        this.nome = nome;
    }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}