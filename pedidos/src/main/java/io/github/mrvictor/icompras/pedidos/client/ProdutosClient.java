package io.github.mrvictor.icompras.pedidos.client;

import io.github.mrvictor.icompras.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtos", url = "${icompras.pedido.clients.produtos.url}")
public interface ProdutosClient {

    /*
        Aqui vamos basicamente copiar a mesma assinatura dos métodos que vamos fazer de outro microserviço, e como é
        apenas a assinatura, não tem corpo o método, pois a implementação correta está no proprio microserviço.
     */
    @GetMapping("{codigo}")
    ResponseEntity<ProdutoRepresentation> obterDados(@PathVariable Long codigo);
}