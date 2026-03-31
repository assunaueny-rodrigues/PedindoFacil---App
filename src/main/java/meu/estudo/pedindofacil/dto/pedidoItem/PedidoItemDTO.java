package meu.estudo.pedindofacil.dto.pedidoItem;

import java.math.BigDecimal;

public record PedidoItemDTO(
    Long id,
    String nome,
    BigDecimal preco,
    Integer quantidade
) {}
