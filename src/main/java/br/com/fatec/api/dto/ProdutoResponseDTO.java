package br.com.fatec.api.dto;

import br.com.fatec.api.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de produto")
public record ProdutoResponseDTO(

        Long id,
        String nome,
        Double preco,
        Long categoriaId,
        String categoriaNome

) {
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