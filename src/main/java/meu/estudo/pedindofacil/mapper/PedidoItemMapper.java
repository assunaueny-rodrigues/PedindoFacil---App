package meu.estudo.pedindofacil.mapper;

import meu.estudo.pedindofacil.dto.pedidoItem.PedidoItemDTO;
import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import meu.estudo.pedindofacil.entity.pedidoItem.PedidoItemEntity;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoItemMapper {
    public PedidoItemDTO toDTO(PedidoItemEntity pedidoItemEntity) {

        return new PedidoItemDTO(
            pedidoItemEntity.getId(),
            pedidoItemEntity.getProduto().getNome(),
            pedidoItemEntity.getProduto().getPreco(),
            pedidoItemEntity.getQuantidade()
        );
    }

    public PedidoItemEntity toEntity(PedidoItemDTO pedidoItem, ProdutoEntity produtoEntity, PedidoEntity pedidoEntity) {
        PedidoItemEntity pedidoItemEntity = new PedidoItemEntity();
        pedidoItemEntity.setProduto(produtoEntity);
        pedidoItemEntity.setQuantidade(pedidoItem.quantidade());
        pedidoItemEntity.setPedido(pedidoEntity);

        BigDecimal valorTotal = produtoEntity.getPreco().multiply(BigDecimal.valueOf(pedidoItem.quantidade()));
        pedidoItemEntity.setValorTotal(valorTotal);

        return pedidoItemEntity;
    }
}
