package io.github.mrvictor.icompras.pedidos.controller.mappers;

import io.github.mrvictor.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido map(NovoPedidoDTO novoPedidoDTO);
}