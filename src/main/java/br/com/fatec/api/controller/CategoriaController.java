package br.com.fatec.api.controller;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // 🔹 LISTAR + FILTRAR (AGORA EM UM ÚNICO ENDPOINT)
    @Operation(summary = "Listar ou buscar categorias",
               description = "Retorna categorias paginadas, podendo filtrar por nome")
    @GetMapping
    public Page<CategoriaResponseDTO> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        if (nome != null && !nome.isEmpty()) {
            return service.buscarPorNome(nome, pageable);
        }

        return service.listar(pageable);
    }

    // 🔹 CRIAR CATEGORIA
    @PostMapping
    @Operation(summary = "Criar categoria")
    public ResponseEntity<CategoriaResponseDTO> criar(
            @Valid @RequestBody CategoriaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }
}