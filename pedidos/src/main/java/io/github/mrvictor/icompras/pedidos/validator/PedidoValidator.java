package io.github.mrvictor.icompras.pedidos.validator;

import feign.FeignException;
import io.github.mrvictor.icompras.pedidos.client.ClientesClient;
import io.github.mrvictor.icompras.pedidos.client.ProdutosClient;
import io.github.mrvictor.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.mrvictor.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();

        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validatItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            ResponseEntity<ClienteRepresentation> response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();

            if(cliente != null) {
                log.info("Cliente de código {} encontrado: {}", cliente.codigo(), cliente.nome());
            }
        } catch(FeignException.NotFound ex) {
            log.error("Cliente não encontrado");
        }
    }

    private void validatItem(ItemPedido itemPedido) {
        try {
            ResponseEntity<ProdutoRepresentation> response = produtosClient.obterDados(itemPedido.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();

            if(produto != null) {
                log.info("Produto de código {} encontrado: {}", produto.codigo(), produto.nome());
            }
        } catch(FeignException.NotFound ex) {
            log.error("Produto não encontrado");
        }
    }
}