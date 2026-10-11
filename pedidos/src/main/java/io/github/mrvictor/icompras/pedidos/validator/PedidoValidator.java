package io.github.mrvictor.icompras.pedidos.validator;

import io.github.mrvictor.icompras.pedidos.client.ClientesClient;
import io.github.mrvictor.icompras.pedidos.client.ProdutosClient;
import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();

        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validatItem);
    }

    private void validarCliente(Long codigoCliente) {

    }

    private void validatItem(ItemPedido itemPedido) {

    }
}