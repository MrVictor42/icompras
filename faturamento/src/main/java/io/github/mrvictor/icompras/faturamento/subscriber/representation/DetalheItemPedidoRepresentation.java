package io.github.mrvictor.icompras.faturamento.subscriber.representation;

import java.math.BigDecimal;

public record DetalheItemPedidoRepresentation(
    Long codigoProduto, String nome, Integer quantidade, BigDecimal valorUnitario, BigDecimal total
) {

}