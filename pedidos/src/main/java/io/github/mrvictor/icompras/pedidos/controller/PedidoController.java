package io.github.mrvictor.icompras.pedidos.controller;

import io.github.mrvictor.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.mrvictor.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.mrvictor.icompras.pedidos.model.ErroResposta;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.model.exception.ValidationException;
import io.github.mrvictor.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDTO) {
        try {
            Pedido pedido = pedidoMapper.map(novoPedidoDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(pedido).getCodigo());
        } catch(ValidationException e) {
            ErroResposta erroResposta = new ErroResposta("Erro validação", e.getField(), e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
        }
    }
}