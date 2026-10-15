package io.github.mrvictor.icompras.pedidos.validator;

import feign.FeignException;
import io.github.mrvictor.icompras.pedidos.client.ClientesClient;
import io.github.mrvictor.icompras.pedidos.client.ProdutosClient;
import io.github.mrvictor.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.mrvictor.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.model.exception.ValidationException;
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

            if(cliente != null && !cliente.ativo()) {
                throw new ValidationException("codigoCliente", "Cliente Inativo");
            }
        } catch(FeignException.NotFound ex) {
            String message = String.format("Cliente de código %d não encontrado", codigoCliente);

            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validatItem(ItemPedido itemPedido) {
        try {
            ResponseEntity<ProdutoRepresentation> response = produtosClient.obterDados(itemPedido.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();

            if(produto != null) {
                log.info("Produto de código {} encontrado: {}", produto.codigo(), produto.nome());
            }

            if(produto != null && !produto.ativo()) {
                throw new ValidationException("codigoProduto", "Produto Inativo");
            }
        } catch(FeignException.NotFound ex) {
            String message = String.format("Produto de código %d não encontrado", itemPedido.getCodigoProduto());

            throw new ValidationException("codigoProduto", message);
        }
    }
}