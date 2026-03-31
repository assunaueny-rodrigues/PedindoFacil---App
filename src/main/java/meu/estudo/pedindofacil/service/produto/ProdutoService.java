package meu.estudo.pedindofacil.service.produto;

import meu.estudo.pedindofacil.dto.produto.ProdutoDTO;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import meu.estudo.pedindofacil.mapper.ProdutoMapper;
import meu.estudo.pedindofacil.repository.produto.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoService(
        ProdutoRepository produtoRepository,
        ProdutoMapper produtoMapper
    ) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoDTO salvar(ProdutoDTO produto) {
        var produtoEntity = produtoMapper.toEntity(produto);
        var produtoSalvo = produtoRepository.save(produtoEntity);

        return produtoMapper.toDTO(produtoSalvo);
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

        return produtoMapper.toDTO(produtoAtualizado);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
        .stream()
        .map(produtoMapper::toDTO)
        .toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return produtoMapper.toDTO(produtoEntity);
    }
}
