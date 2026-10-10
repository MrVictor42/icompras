package io.github.mrvictor.icompras.pedidos.controller.dto;

import java.util.List;

public record NovoPedidoDTO(
    Long codigoCliente, List<ItemPedidoDTO> itens, DadosPagamentoDTO dadosPagamentoDTO
) {

}