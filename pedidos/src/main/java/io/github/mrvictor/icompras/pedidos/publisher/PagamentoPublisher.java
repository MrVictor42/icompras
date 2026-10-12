package io.github.mrvictor.icompras.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalhePedidoMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar(Pedido pedido) {
        log.info("Publicando pedido pago {}", pedido.getCodigo());

        try {
            DetalhePedidoRepresentation representation = detalhePedidoMapper.map(pedido);
            String json = objectMapper.writeValueAsString(representation);

            kafkaTemplate.send(topico, "dados", json);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar json: {}", e.getMessage());
        } catch(RuntimeException e) {
            log.error("Erro técnico ao publicar no tópico de pedidos: {}", e.getMessage());
        }
    }
}