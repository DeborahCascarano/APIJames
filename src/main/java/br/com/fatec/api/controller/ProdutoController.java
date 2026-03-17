package br.com.fatec.api.controller;

import br.com.fatec.api.dto.ProdutoRequestDTO;
import br.com.fatec.api.dto.ProdutoResponseDTO;
import br.com.fatec.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos") // Prefixo da rota
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    //    @GetMapping
//    public List<Produto> listar() {
//
//        return service.listarTodos();
//    }
    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return service.listarTodos();
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Produto> buscar(@PathVariable Long id) {
//        return service.buscarPorId(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //    @PostMapping
//    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(service.salvar(produto));
//    }
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
//        try {
//            return ResponseEntity.ok(service.atualizar(id, produto));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable Long id) {
//        service.deletar(id);
//        return ResponseEntity.noContent().build(); // Retorna 204 No Content
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}