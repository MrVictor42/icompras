package io.github.mrvictor.icompras.produtos.controller;

import io.github.mrvictor.icompras.produtos.model.Produto;
import io.github.mrvictor.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        produtoService.salvar(produto);

        return ResponseEntity.ok(produto);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterDados(@PathVariable Long codigo) {
        return produtoService
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}