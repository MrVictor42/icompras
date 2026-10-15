package io.github.mrvictor.icompras.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrvictor.icompras.faturamento.mapper.PedidoMapper;
import io.github.mrvictor.icompras.faturamento.model.Pedido;
import io.github.mrvictor.icompras.faturamento.service.GeradorFiscalService;
import io.github.mrvictor.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final PedidoMapper pedidoMapper;
    private final GeradorFiscalService geradorFiscalService;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json) {
        try {
            log.info("Recebendo pedido para faturamento: {}", json);

            DetalhePedidoRepresentation detalhePedidoRepresentation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = pedidoMapper.map(detalhePedidoRepresentation);

            geradorFiscalService.gerar(pedido);
        } catch(Exception ex) {
            log.error("Erro na consumação do tópico de pedidos pagos: {}", ex.getMessage());
        }
    }
}