package meu.estudo.pedindofacil.service.produto;

import meu.estudo.pedindofacil.dto.produto.ProdutoDTO;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import meu.estudo.pedindofacil.repository.produto.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDTO salvar(ProdutoDTO produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(produto.nome());
        produtoEntity.setPreco(produto.preco());

        var produtoSalvo = produtoRepository.save(produtoEntity);

        return new ProdutoDTO(
            produtoSalvo.getId(),
            produtoSalvo.getNome(),
            produtoSalvo.getPreco()
        );
    }

    public void excluir(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepository.delete(produtoEntity);
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO produto) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoEntity.setNome(produto.nome());
        produtoEntity.setPreco(produto.preco());

        var produtoAtualizado = produtoRepository.save(produtoEntity);

        return new ProdutoDTO(
            produtoAtualizado.getId(),
            produtoAtualizado.getNome(),
            produtoAtualizado.getPreco()
        );
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
        .stream()
        .map(produto -> new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco()
        ))
        .toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return new ProdutoDTO(
            produtoEntity.getId(),
            produtoEntity.getNome(),
            produtoEntity.getPreco()
        );
    }
}
