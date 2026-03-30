package meu.estudo.pedindofacil.service;

import meu.estudo.pedindofacil.dto.ProdutoRequest;
import meu.estudo.pedindofacil.dto.ProdutoResponse;
import meu.estudo.pedindofacil.entity.ProdutoEntity;
import meu.estudo.pedindofacil.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse salvar(ProdutoRequest produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(produto.nome());
        produtoEntity.setPreco(produto.preco());

        var produtoSalvo = produtoRepository.save(produtoEntity);

        return new ProdutoResponse(
            produtoSalvo.getId(),
            produtoSalvo.getNome(),
            produtoSalvo.getPreco()
        );
    }

    public void excluir(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepository.delete(produtoEntity);
    }
}
