package io.github.mrvictor.icompras.pedidos.service;

import io.github.mrvictor.icompras.pedidos.model.enums.StatusPedido;
import io.github.mrvictor.icompras.pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio) {

    }
}