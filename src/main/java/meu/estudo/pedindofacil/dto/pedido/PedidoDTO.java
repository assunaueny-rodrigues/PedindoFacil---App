package meu.estudo.pedindofacil.dto.pedido;

import meu.estudo.pedindofacil.dto.pedidoItem.PedidoItemDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record PedidoDTO(
    Long id,
    Date dataPedido,
    String nomeCliente,
    BigDecimal valorTotal,
    List<PedidoItemDTO> itens
) {}
