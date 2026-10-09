package io.github.mrvictor.icompras.pedidos.service;

import io.github.mrvictor.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.mrvictor.icompras.pedidos.repository.PedidoRepository;
import io.github.mrvictor.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;


}