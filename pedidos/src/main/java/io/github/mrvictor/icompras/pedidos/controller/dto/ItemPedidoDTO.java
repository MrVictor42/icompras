package io.github.mrvictor.icompras.pedidos.controller.dto;

import java.math.BigDecimal;

public record ItemPedidoDTO(
    Long codigoCliente, Integer quantidade, BigDecimal valorUnitario
) {

}