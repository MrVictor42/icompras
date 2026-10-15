package io.github.mrvictor.icompras.pedidos.service;

import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.model.enums.StatusPedido;
import io.github.mrvictor.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.mrvictor.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio) {
        Pedido pedido = pedidoRepository.findById(codigo).orElseThrow(() ->
            new ItemNaoEncontradoException("Pedido não encontrado com o código: {}" + codigo)
        );

        pedido.setStatus(status);
        pedido.setUrlNotaFiscal(urlNotaFiscal);
        pedido.setCodigoRastreio(codigoRastreio);
    }
}