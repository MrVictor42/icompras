package io.github.mrvictor.icompras.pedidos.service;

import io.github.mrvictor.icompras.pedidos.client.ServicoBancarioClient;
import io.github.mrvictor.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.mrvictor.icompras.pedidos.model.DadosPagamento;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import io.github.mrvictor.icompras.pedidos.model.enums.StatusPedido;
import io.github.mrvictor.icompras.pedidos.model.enums.TipoPagamento;
import io.github.mrvictor.icompras.pedidos.model.exception.ValidationException;
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

    public void atualizarStatusPagamento(RecebimentoCallbackPagamentoDTO recebimentoCallbackPagamentoDTO) {
        Pedido pedido = pedidoRepository.findByCodigoAndChavePagamento(
            recebimentoCallbackPagamentoDTO.codigo(), recebimentoCallbackPagamentoDTO.chavePagamento()
        ).orElseThrow(() -> new ValidationException(
            "Pedido Não Encontrado",
            "Pedido não encontrado para o código " + recebimentoCallbackPagamentoDTO.codigo() +
            " e a chave pagamento " + recebimentoCallbackPagamentoDTO.chavePagamento()
        ));

        if(recebimentoCallbackPagamentoDTO.status()) {
            pedido.setStatus(StatusPedido.PAGO);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
        }

        pedido.setObservacoes(recebimentoCallbackPagamentoDTO.observacoes());

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
        Pedido pedido = pedidoRepository.findById(codigoPedido).orElseThrow(() -> new ValidationException(
            "Pedido Não Encontrado",
            "Pedido não encontrado para o código " + codigoPedido
        ));
        DadosPagamento dadosPagamento = new DadosPagamento();

        dadosPagamento.setTipoPagamento(tipoPagamento);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo Pagamento Realizado!! Aguardando o novo processamento.");

        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);

        pedido.setChavePagamento(chavePagamento);

        pedidoRepository.save(pedido);
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