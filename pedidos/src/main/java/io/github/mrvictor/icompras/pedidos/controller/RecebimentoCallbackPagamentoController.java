package io.github.mrvictor.icompras.pedidos.controller;

import io.github.mrvictor.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.mrvictor.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
        @RequestBody RecebimentoCallbackPagamentoDTO recebimentoCallbackPagamentoDTO,
        @RequestHeader(name = "apiKey") String apiKey
    ) {
        pedidoService.atualizarStatusPagamento(recebimentoCallbackPagamentoDTO);

        return ResponseEntity.ok().build();
    }
}