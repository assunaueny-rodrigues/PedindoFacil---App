package meu.estudo.pedindofacil.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
   Long id,
   String nome,
   BigDecimal preco
) {}
