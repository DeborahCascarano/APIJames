package br.com.fatec.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    // 🔹 LISTAR TODOS
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista paginada de produtos")
    @GetMapping
    public Page<ProdutoResponseDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        return service.listarTodos(pageable);
    }

    // 🔹 BUSCAR POR NOME
    @GetMapping(params = "nome")
    @Operation(summary = "Buscar produto por nome", description = "Retorna uma lista paginada de produtos com o nome especificado")
    public Page<ProdutoResponseDTO> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {

        return service.buscarPorNome(nome, pageable);
    }

    // 🔥 NOVO — BUSCAR POR CATEGORIA
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna uma lista paginada de produtos de uma categoria específica")
    public Page<ProdutoResponseDTO> buscarPorCategoria(
            @PathVariable Long categoriaId,
            @PageableDefault(size = 10) Pageable pageable) {

        return service.buscarPorCategoria(categoriaId, pageable);
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 CRIAR
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    // 🔹 ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // 🔹 DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}