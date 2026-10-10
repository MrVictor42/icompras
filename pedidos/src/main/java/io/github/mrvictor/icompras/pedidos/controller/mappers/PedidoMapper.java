package io.github.mrvictor.icompras.pedidos.controller.mappers;

import io.github.mrvictor.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.mrvictor.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.mrvictor.icompras.pedidos.enums.StatusPedido;
import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    /*
        source Itens -> novoPedidoDTO
        target Itens -> pedido
     */
    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    Pedido map(NovoPedidoDTO novoPedidoDTO);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> itemPedidoDTOS) {
        return itemPedidoDTOS.stream().map(ITEM_PEDIDO_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setTotal(calcularTotal(pedido));
    }

    private static @NonNull BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream().map(item ->
                item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}