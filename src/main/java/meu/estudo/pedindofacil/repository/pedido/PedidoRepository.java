package meu.estudo.pedindofacil.repository.pedido;

import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {}
