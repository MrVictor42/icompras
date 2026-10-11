package io.github.mrvictor.icompras.pedidos.service;

import io.github.mrvictor.icompras.pedidos.client.ServicoBancarioClient;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.mrvictor.icompras.pedidos.repository.PedidoRepository;
import io.github.mrvictor.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;

    /*
        Para não salvar o pedido antes e depois que conseguir a chave pagamento abrir outra transação e salvar de novo
        com a anotação @Transactional tudo virá uma transação. Então no final, colocando o pedido.setChavePagamento
        o pedido já vai salvo para o banco, ainda que esteja depois da linha pedidoReposiory.save(pedido);
     */
    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        pedidoValidator.validar(pedido);

        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);

        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);

        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}