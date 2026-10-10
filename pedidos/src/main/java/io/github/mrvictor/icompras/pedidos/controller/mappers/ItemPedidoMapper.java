package io.github.mrvictor.icompras.pedidos.controller.mappers;

import io.github.mrvictor.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO itemPedidoDTO);
}