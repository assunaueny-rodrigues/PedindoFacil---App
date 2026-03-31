package meu.estudo.pedindofacil.service.pedido;

import meu.estudo.pedindofacil.dto.pedidoItem.PedidoItemDTO;
import meu.estudo.pedindofacil.dto.pedido.PedidoDTO;
import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import meu.estudo.pedindofacil.entity.pedidoItem.PedidoItemEntity;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import meu.estudo.pedindofacil.repository.pedido.PedidoRepository;
import meu.estudo.pedindofacil.repository.produto.ProdutoRepository;
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

    public PedidoDTO salvar(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setNomeCliente(pedidoDTO.nomeCliente());
        pedidoEntity.setDataPedido(pedidoDTO.dataPedido());

        List<PedidoItemEntity> itensEntidade =
            pedidoDTO.itens().stream()
                .map(itemRequest -> criarPedidoItem(itemRequest, pedidoEntity))
                .toList();

        pedidoEntity.getItens().addAll(itensEntidade);

        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);

        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);

        var itensComTodosDados = criarListaDeItens(pedidoSalvo.getItens());

        return new PedidoDTO(
            pedidoSalvo.getId(),
            pedidoSalvo.getDataPedido(),
            pedidoSalvo.getNomeCliente(),
            pedidoSalvo.getValorTotal(),
            itensComTodosDados
        );
    }

    private PedidoItemEntity criarPedidoItem(PedidoItemDTO pedidoItem, PedidoEntity pedido) {
        ProdutoEntity produto = produtoRepository.findById(pedidoItem.id())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + pedidoItem.id()));

        PedidoItemEntity item = new PedidoItemEntity();
        item.setProduto(produto);
        item.setQuantidade(pedidoItem.quantidade());
        item.setPedido(pedido);

        BigDecimal valorTotal = produto.getPreco().multiply(BigDecimal.valueOf(pedidoItem.quantidade()));
        item.setValorTotal(valorTotal);

        return item;
    }

    private BigDecimal somarValorTotal(List<PedidoItemEntity> pedidoItemEntities) {
        return pedidoItemEntities.stream()
            .map(PedidoItemEntity::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PedidoItemDTO> criarListaDeItens(List<PedidoItemEntity> itens) {
        return itens.stream()
            .map(item -> {
                var produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.getProduto().getId()));

                return new PedidoItemDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    item.getQuantidade()
                );
            })
            .toList();
    }

    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedido -> new PedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getNomeCliente(),
                pedido.getValorTotal(),
                pedido.getItens().stream()
                .map(item -> new PedidoItemDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
                ))
                .toList()
            ))
            .toList();
    }

    public PedidoDTO buscarPorId(Long id) {
        PedidoEntity pedidoEntity =  pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        var itens = pedidoEntity.getItens().stream()
            .map(item -> new PedidoItemDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
            ))
            .toList();

        return new PedidoDTO(
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

    public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTO){
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        pedidoEntity.setNomeCliente(pedidoDTO.nomeCliente());
        pedidoEntity.setDataPedido(pedidoDTO.dataPedido());

        // Limpar itens antigos (orphanRemoval = true vai deletar automaticamente)
        pedidoEntity.getItens().clear();

        List<PedidoItemEntity> itensEntidade =
            pedidoDTO.itens().stream()
                    .map(itemRequest -> criarPedidoItem(itemRequest, pedidoEntity))
                    .toList();

        pedidoEntity.getItens().addAll(itensEntidade);

        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);


        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);

        var itensComTodosDados = criarListaDeItens(pedidoSalvo.getItens());

        return new PedidoDTO(
            pedidoSalvo.getId(),
            pedidoSalvo.getDataPedido(),
            pedidoSalvo.getNomeCliente(),
            pedidoSalvo.getValorTotal(),
            itensComTodosDados
        );
    }
}
