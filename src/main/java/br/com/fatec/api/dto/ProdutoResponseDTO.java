package br.com.fatec.api.dto;

import br.com.fatec.api.model.Produto;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private Double preco;

    // 🔥 NOVOS CAMPOS
    private Long categoriaId;
    private String categoriaNome;

    public ProdutoResponseDTO(Long id, String nome, Double preco,
                              Long categoriaId, String categoriaNome) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    // 🔥 CONVERSÃO ATUALIZADA
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome()
        );
    }
}