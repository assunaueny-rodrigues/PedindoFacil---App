package meu.estudo.pedindofacil.dto;

import java.math.BigDecimal;

public record ProdutoRequest(
    String nome,
    BigDecimal preco
) {}
