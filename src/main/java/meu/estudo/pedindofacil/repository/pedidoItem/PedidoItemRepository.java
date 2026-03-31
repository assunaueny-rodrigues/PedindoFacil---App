package meu.estudo.pedindofacil.repository.pedidoItem;

import meu.estudo.pedindofacil.entity.pedidoItem.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {
}
