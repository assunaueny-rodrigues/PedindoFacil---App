package meu.estudo.pedindofacil.dto.produto;

import java.math.BigDecimal;

public record ProdutoDTO(
   Long id,
   String nome,
   BigDecimal preco
) {}
