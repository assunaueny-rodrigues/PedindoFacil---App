package meu.estudo.pedindofacil.repository;

import meu.estudo.pedindofacil.entity.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {
}
