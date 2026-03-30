package meu.estudo.pedindofacil.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record PedidoResponse (
    Long id,
    Date dataPedido,
    String nomeCliente,
    BigDecimal valorTotal,
    List<PedidoItemResponse> itens
) {}
