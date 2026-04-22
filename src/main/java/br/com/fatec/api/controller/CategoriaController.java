package br.com.fatec.api.controller;

import br.com.fatec.api.dto.CategoriaRequestDTO;
import br.com.fatec.api.dto.CategoriaResponseDTO;
import br.com.fatec.api.service.CategoriaService;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(
        summary = "Listar ou buscar categorias",
        description = "Retorna categorias paginadas, podendo filtrar por nome"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Categorias retornadas com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class))
        )
    })
    @GetMapping
    public Page<CategoriaResponseDTO> listar(
            @Parameter(description = "Filtrar pelo nome da categoria")
            @RequestParam(required = false) String nome,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            @ParameterObject Pageable pageable) {

        if (nome != null && !nome.isEmpty()) {
            return service.buscarPorNome(nome, pageable);
        }
        return service.listar(pageable);
    }

    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class))
        )
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
            @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }
}