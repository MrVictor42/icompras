package io.github.mrvictor.icompras.pedidos.repository;

import io.github.mrvictor.icompras.pedidos.model.ItemPedido;
import io.github.mrvictor.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByPedido(Pedido pedido);
}