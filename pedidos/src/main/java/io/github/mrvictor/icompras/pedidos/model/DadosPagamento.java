package io.github.mrvictor.icompras.pedidos.model;

import io.github.mrvictor.icompras.pedidos.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;
}