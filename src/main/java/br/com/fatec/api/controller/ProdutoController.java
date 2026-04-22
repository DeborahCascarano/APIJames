package br.com.fatec.api.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Produtos", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista paginada de produtos")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produtos retornados com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        )
    })
    @GetMapping
    public Page<ProdutoResponseDTO> listar(
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            @ParameterObject Pageable pageable) {
        return service.listarTodos(pageable);
    }

    @GetMapping(params = "nome")
    @Operation(summary = "Buscar produto por nome", description = "Retorna uma lista paginada de produtos com o nome especificado")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produtos encontrados com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        )
    })
    public Page<ProdutoResponseDTO> buscarPorNome(
            @Parameter(description = "Nome do produto para filtrar")
            @RequestParam String nome,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            @ParameterObject Pageable pageable) {
        return service.buscarPorNome(nome, pageable);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna uma lista paginada de produtos de uma categoria específica")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produtos da categoria retornados com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        ),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    public Page<ProdutoResponseDTO> buscarPorCategoria(
            @Parameter(description = "ID da categoria") @PathVariable Long categoriaId,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC)
            @ParameterObject Pageable pageable) {
        return service.buscarPorCategoria(categoriaId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        ),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> buscar(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        )
    })
    public ResponseEntity<ProdutoResponseDTO> criar(
            @Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ProdutoResponseDTO.class))
        ),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @Parameter(description = "ID do produto") @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}