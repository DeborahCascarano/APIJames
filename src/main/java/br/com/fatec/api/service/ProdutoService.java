package br.com.fatec.api.service;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.model.Produto;
import br.com.fatec.api.model.Categoria;
import br.com.fatec.api.repository.ProdutoRepository;
import br.com.fatec.api.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 🔹 LISTAR PAGINADO
    public Page<ProdutoResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

    // 🔹 BUSCAR POR NOME
    public Page<ProdutoResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

    // 🔹 BUSCAR POR ID
    public Optional<ProdutoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(ProdutoResponseDTO::fromEntity);
    }

    // 🔹 SALVAR COM CATEGORIA
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        Produto salvo = repository.save(produto);
        return ProdutoResponseDTO.fromEntity(salvo);
    }

    // 🔹 ATUALIZAR COM CATEGORIA
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {

        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setNome(dto.nome());
        existente.setPreco(dto.preco());

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existente.setCategoria(categoria);

        Produto atualizado = repository.save(existente);
        return ProdutoResponseDTO.fromEntity(atualizado);
    }

    // 🔹 DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Produto não encontrado com ID " + id);
        }
        repository.deleteById(id);
    }

    // 🔹 BUSCAR POR CATEGORIA
    public Page<ProdutoResponseDTO> buscarPorCategoria(Long categoriaId, Pageable pageable) {
        return repository.findByCategoriaId(categoriaId, pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }
}