package io.github.mrvictor.icompras.produtos.repository;

import io.github.mrvictor.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}