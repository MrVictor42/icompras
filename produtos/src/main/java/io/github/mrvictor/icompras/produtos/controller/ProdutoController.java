package io.github.mrvictor.icompras.produtos.controller;

import io.github.mrvictor.icompras.produtos.model.Produto;
import io.github.mrvictor.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produto));
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterDados(@PathVariable Long codigo) {
        return produtoService
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
        Produto produto = produtoService.obterPorCodigo(codigo).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Produto Inexistente"
        ));

        produtoService.deletar(produto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Produto>> obterTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.obterTodos());
    }
}