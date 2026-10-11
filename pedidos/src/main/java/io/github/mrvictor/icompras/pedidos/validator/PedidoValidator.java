package io.github.mrvictor.icompras.pedidos.validator;

import io.github.mrvictor.icompras.pedidos.client.ProdutosClient;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;

    public void validar(Pedido pedido) {

    }
}