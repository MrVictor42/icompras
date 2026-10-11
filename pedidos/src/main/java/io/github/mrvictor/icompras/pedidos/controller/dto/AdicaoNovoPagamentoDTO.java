package io.github.mrvictor.icompras.pedidos.controller.dto;

import io.github.mrvictor.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento) {

}