package io.github.mrvictor.icompras.produtos.service;

import io.github.mrvictor.icompras.produtos.model.Produto;
import io.github.mrvictor.icompras.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo) {
        return produtoRepository.findById(codigo);
    }
}