package meu.estudo.pedindofacil.service;

import meu.estudo.pedindofacil.dto.PedidoItemRequest;
import meu.estudo.pedindofacil.dto.PedidoItemResponse;
import meu.estudo.pedindofacil.dto.PedidoRequest;
import meu.estudo.pedindofacil.dto.PedidoResponse;
import meu.estudo.pedindofacil.entity.PedidoEntity;
import meu.estudo.pedindofacil.entity.PedidoItemEntity;
import meu.estudo.pedindofacil.entity.ProdutoEntity;
import meu.estudo.pedindofacil.repository.PedidoRepository;
import meu.estudo.pedindofacil.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(
        PedidoRepository pedidoRepository,
        ProdutoRepository produtoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoResponse salvar(PedidoRequest pedidoRequest) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setNomeCliente(pedidoRequest.nomeCliente());
        pedidoEntity.setDataPedido(pedidoRequest.dataPedido());

        List<PedidoItemEntity> itensEntidade =
            pedidoRequest.itens().stream()
                .map(itemRequest -> criarPedidoItem(itemRequest, pedidoEntity))
                .toList();

        pedidoEntity.getItens().addAll(itensEntidade);

        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);

        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);

        var itensComTodosDados = criarListaDeItens(pedidoSalvo.getItens());

        return new PedidoResponse(
            pedidoSalvo.getId(),
            pedidoSalvo.getDataPedido(),
            pedidoSalvo.getNomeCliente(),
            pedidoSalvo.getValorTotal(),
            itensComTodosDados
        );
    }

    private PedidoItemEntity criarPedidoItem(PedidoItemRequest itemRequest, PedidoEntity pedido) {
        ProdutoEntity produto = produtoRepository.findById(itemRequest.id())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemRequest.id()));

        PedidoItemEntity item = new PedidoItemEntity();
        item.setProduto(produto);
        item.setQuantidade(itemRequest.quantidade());
        item.setPedido(pedido);

        BigDecimal valorTotal = produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.quantidade()));
        item.setValorTotal(valorTotal);

        return item;
    }

    private BigDecimal somarValorTotal(List<PedidoItemEntity> pedidoItemEntities) {
        return pedidoItemEntities.stream()
            .map(PedidoItemEntity::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PedidoItemResponse> criarListaDeItens(List<PedidoItemEntity> itens) {
        return itens.stream()
            .map(item -> {
                var produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.getProduto().getId()));

                return new PedidoItemResponse(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    item.getQuantidade()
                );
            })
            .toList();
    }

    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedido -> new PedidoResponse(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getNomeCliente(),
                pedido.getValorTotal(),
                pedido.getItens().stream()
                .map(item -> new PedidoItemResponse(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
                ))
                .toList()
            ))
            .toList();
    }

    public PedidoResponse buscarPorId(Long id) {
        PedidoEntity pedidoEntity =  pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        var itens = pedidoEntity.getItens().stream()
            .map(item -> new PedidoItemResponse(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
            ))
            .toList();

        return new PedidoResponse(
            pedidoEntity.getId(),
            pedidoEntity.getDataPedido(),
            pedidoEntity.getNomeCliente(),
            pedidoEntity.getValorTotal(),
            itens
        );
    }

    public void excluir(Long id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        pedidoRepository.delete(pedidoEntity);
    }

    public PedidoResponse atualizar(Long id, PedidoRequest pedidoRequest){
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        pedidoEntity.setNomeCliente(pedidoRequest.nomeCliente());
        pedidoEntity.setDataPedido(pedidoRequest.dataPedido());

        // Limpar itens antigos (orphanRemoval = true vai deletar automaticamente)
        pedidoEntity.getItens().clear();

        List<PedidoItemEntity> itensEntidade =
            pedidoRequest.itens().stream()
                    .map(itemRequest -> criarPedidoItem(itemRequest, pedidoEntity))
                    .toList();

        pedidoEntity.getItens().addAll(itensEntidade);

        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);


        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);

        var itensComTodosDados = criarListaDeItens(pedidoSalvo.getItens());

        return new PedidoResponse(
            pedidoSalvo.getId(),
            pedidoSalvo.getDataPedido(),
            pedidoSalvo.getNomeCliente(),
            pedidoSalvo.getValorTotal(),
            itensComTodosDados
        );
    }
}
