package io.github.mrvictor.icompras.pedidos.controller.dto;

import io.github.mrvictor.icompras.pedidos.enums.TipoPagamento;

public record DadosPagamentoDTO(
    String dados, TipoPagamento tipoPagamento
) {

}