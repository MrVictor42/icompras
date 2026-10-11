package io.github.mrvictor.icompras.pedidos.controller.dto;

import io.github.mrvictor.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {

}