package io.github.mrvictor.icompras.logistica.subscriber.representation;

import io.github.mrvictor.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(Long codigo, StatusPedido status, String urlNotaFiscal) {

}