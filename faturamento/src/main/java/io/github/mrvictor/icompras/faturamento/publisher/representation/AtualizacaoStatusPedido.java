package io.github.mrvictor.icompras.faturamento.publisher.representation;

import io.github.mrvictor.icompras.faturamento.model.enums.StatusPedido;

public record AtualizacaoStatusPedido(Long codigo, StatusPedido status, String urlNotaFiscal) {

}