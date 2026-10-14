package io.github.mrvictor.icompras.faturamento.mapper;

import io.github.mrvictor.icompras.faturamento.model.Cliente;
import io.github.mrvictor.icompras.faturamento.model.ItemPedido;
import io.github.mrvictor.icompras.faturamento.model.Pedido;
import io.github.mrvictor.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import io.github.mrvictor.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation detalhePedido) {
        Cliente cliente = new Cliente(
            detalhePedido.nome(), detalhePedido.cpf(), detalhePedido.logradouro(), detalhePedido.numero(),
            detalhePedido.bairro(), detalhePedido.email(), detalhePedido.telefone()
        );

        List<ItemPedido> itens = detalhePedido.itens().stream().map(this::mapItem).toList();

        return new Pedido(detalhePedido.codigo(), cliente, detalhePedido.dataPedido(), detalhePedido.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation detalheItemPedido) {
        return new ItemPedido(
            detalheItemPedido.codigoProduto(), detalheItemPedido.nome(), detalheItemPedido.valorUnitario(), detalheItemPedido.quantidade()
        );
    }
}