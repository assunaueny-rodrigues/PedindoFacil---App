package meu.estudo.pedindofacil.mapper;

import meu.estudo.pedindofacil.dto.produto.ProdutoDTO;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public ProdutoDTO toDTO(ProdutoEntity produtoEntity) {
        return new ProdutoDTO(
            produtoEntity.getId(),
            produtoEntity.getNome(),
            produtoEntity.getPreco()
        );
    }

    public ProdutoEntity toEntity(ProdutoDTO produtoDTO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produtoDTO.id());
        produtoEntity.setNome(produtoDTO.nome());
        produtoEntity.setPreco(produtoDTO.preco());
        return produtoEntity;
    }
}
