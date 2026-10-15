package io.github.mrvictor.icompras.faturamento.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrvictor.icompras.faturamento.model.Pedido;
import io.github.mrvictor.icompras.faturamento.model.enums.StatusPedido;
import io.github.mrvictor.icompras.faturamento.publisher.representation.AtualizacaoStatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar(Pedido pedido, String urlNotaFiscal) {
        try {
            AtualizacaoStatusPedido statusPedido = new AtualizacaoStatusPedido(
                pedido.codigo(), StatusPedido.FATURADO, urlNotaFiscal
            );

            String json = objectMapper.writeValueAsString(statusPedido);

            kafkaTemplate.send(topico, "dados", json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}