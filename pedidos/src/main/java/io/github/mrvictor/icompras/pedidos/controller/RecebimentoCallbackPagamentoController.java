package io.github.mrvictor.icompras.pedidos.controller;

import io.github.mrvictor.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.mrvictor.icompras.pedidos.model.exception.ValidationException;
import io.github.mrvictor.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
@Slf4j
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
        @RequestBody RecebimentoCallbackPagamentoDTO dto,
        @RequestHeader(name = "apiKey") String apiKey
    ) {
        try {
            pedidoService.atualizarStatusPagamento(dto);
        } catch (ValidationException e) {
            log.error("Erro ao atualizar status do pagamento: {}", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}