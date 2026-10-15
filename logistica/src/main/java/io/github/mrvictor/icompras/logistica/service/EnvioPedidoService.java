package io.github.mrvictor.icompras.logistica.service;

import io.github.mrvictor.icompras.logistica.model.AtualizacaoEnvioPedido;
import io.github.mrvictor.icompras.logistica.model.enums.StatusPedido;
import io.github.mrvictor.icompras.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher publisher;

    public void enviar(Long codigoPedido, String urlNotaFiscal) {
        String codigoRastreio = gerarCodigoRastreio();
        AtualizacaoEnvioPedido envioPedido = new AtualizacaoEnvioPedido(codigoPedido, StatusPedido.ENVIADO, codigoRastreio);

        publisher.enviar(envioPedido);
    }

    private String gerarCodigoRastreio() {
        //AB123456789BR

        Random random = new Random();
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        int numeros = 100000000 + random.nextInt(900000000);

        return "" + letra1 + letra2 + numeros + "BR";
    }
}