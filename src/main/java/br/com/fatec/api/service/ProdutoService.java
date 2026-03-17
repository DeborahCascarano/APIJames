package br.com.fatec.api.service;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.model.Produto;
import br.com.fatec.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    //    public List<Produto> listarTodos() {
//
//        return repository.findAll();
//    }
    // Listar: Converte a lista de Entities para DTOs
    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .toList();
    }

    //    // O buscaPorId agora retorna a "caixa" (Optional)
//    public Optional<Produto> buscarPorId(Long id) {
//
//        return repository.findById(id);
//    }
    // Buscar por ID: Retorna Optional de DTO
    public Optional<ProdutoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(ProdutoResponseDTO::fromEntity);
        // Se a Entity existir, o .map() a transforma em DTO.
        // Se não existir, retorna um Optional vazio.
    }

    //    public Produto salvar(Produto produto) {
//
//        return repository.save(produto);
//    }
    // Salvar: Recebe RequestDTO -> Converte para Entity -> Salva -> Retorna ResponseDTO
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());

        Produto salvo = repository.save(produto);
        return ProdutoResponseDTO.fromEntity(salvo);
    }

    //    public Produto atualizar(Long id, Produto novo) {
//        // Aqui precisamos descompactar o Optional ou lançar erro se estiver vazio
//        Produto existente = buscarPorId(id)
//                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
//
//        existente.setNome(novo.getNome());
//        existente.setPreco(novo.getPreco());
//        return repository.save(existente);
//    }
    // Atualizar: O ponto onde geralmente ocorre o erro de tipos
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        // Busca a Entity no banco
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Transfere os dados do DTO para o Entity
        existente.setNome(dto.nome());
        existente.setPreco(dto.preco());

        // Salva e converte para DTO de saída
        Produto atualizado = repository.save(existente);
        return ProdutoResponseDTO.fromEntity(atualizado);
    }

    //    public void deletar(Long id) {
//        // Mesma lógica: descompacta para poder deletar
//        Produto p = repository.findAllById(id)
//                .orElseThrow(() -> new RuntimeException("Não é possível deletar: ID inexistente"));
//        repository.delete(p);
//    }
    public void deletar(Long id) {
        // Valida se o ID existe antes de chamar o deleteById
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Produto não encontrado com ID " + id);
        }

        // O método correto para um único ID é deleteById
        repository.deleteById(id);
    }
}
