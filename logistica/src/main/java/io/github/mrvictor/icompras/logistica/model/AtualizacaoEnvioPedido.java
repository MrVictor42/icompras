package io.github.mrvictor.icompras.logistica.model;

import io.github.mrvictor.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoEnvioPedido(Long codigo, StatusPedido status, String codigoRastreio) {

}