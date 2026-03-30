package meu.estudo.pedindofacil.dto;

import java.math.BigDecimal;

public record PedidoItemResponse(
    Long id,
    String nome,
    BigDecimal preco,
    Integer quantidade
) {}
