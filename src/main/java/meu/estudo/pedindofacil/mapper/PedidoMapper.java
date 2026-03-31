package meu.estudo.pedindofacil.mapper;

import meu.estudo.pedindofacil.dto.pedido.PedidoDTO;
import meu.estudo.pedindofacil.dto.pedidoItem.PedidoItemDTO;
import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {
    private final PedidoItemMapper pedidoItemMapper = new PedidoItemMapper();

    public PedidoDTO toDTO(PedidoEntity pedidoEntity) {
        List<PedidoItemDTO> itens =
            pedidoEntity.getItens().stream()
                .map(pedidoItemMapper::toDTO)
                .toList();

        return new PedidoDTO(
            pedidoEntity.getId(),
            pedidoEntity.getDataPedido(),
            pedidoEntity.getNomeCliente(),
            pedidoEntity.getValorTotal(),
            itens
        );
    }

    public PedidoEntity toEntity(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setNomeCliente(pedidoDTO.nomeCliente());
        pedidoEntity.setDataPedido(pedidoDTO.dataPedido());
        return pedidoEntity;
    }
}
