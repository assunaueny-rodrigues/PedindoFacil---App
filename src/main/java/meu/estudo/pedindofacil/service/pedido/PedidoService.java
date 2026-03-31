package meu.estudo.pedindofacil.service.pedido;
import jakarta.transaction.Transactional;
import meu.estudo.pedindofacil.dto.pedidoItem.PedidoItemDTO;
import meu.estudo.pedindofacil.dto.pedido.PedidoDTO;
import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import meu.estudo.pedindofacil.entity.pedidoItem.PedidoItemEntity;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import meu.estudo.pedindofacil.mapper.PedidoItemMapper;
import meu.estudo.pedindofacil.mapper.PedidoMapper;
import meu.estudo.pedindofacil.repository.pedido.PedidoRepository;
import meu.estudo.pedindofacil.repository.produto.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemMapper pedidoItemMapper;
    private final PedidoMapper pedidoMapper;


    public PedidoService(
        PedidoRepository pedidoRepository,
        ProdutoRepository produtoRepository,
        PedidoItemMapper pedidoItemMapper,
        PedidoMapper pedidoMapper
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoItemMapper = pedidoItemMapper;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional
    public PedidoDTO salvar(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.toEntity(pedidoDTO);

        List<PedidoItemEntity> itensEntidade =
            pedidoDTO.itens().stream()
                .map(pedidoItem -> criarPedidoItem(pedidoItem, pedidoEntity))
                .toList();

        pedidoEntity.getItens().addAll(itensEntidade);
        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);

        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);

        return pedidoMapper.toDTO(pedidoSalvo);
    }

    private PedidoItemEntity criarPedidoItem(PedidoItemDTO pedidoItemDTO, PedidoEntity pedidoEntity) {
        ProdutoEntity produtoEntity = produtoRepository.findById(pedidoItemDTO.id())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + pedidoItemDTO.id()));

        return pedidoItemMapper.toEntity(pedidoItemDTO, produtoEntity, pedidoEntity);
    }

    private BigDecimal somarValorTotal(List<PedidoItemEntity> pedidoItemEntities) {
        return pedidoItemEntities.stream()
            .map(PedidoItemEntity::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedidoMapper::toDTO)
            .toList();
    }

    public PedidoDTO buscarPorId(Long id) {
        PedidoEntity pedidoEntity =  pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        return pedidoMapper.toDTO(pedidoEntity);
    }

    @Transactional
    public void excluir(Long id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        pedidoRepository.delete(pedidoEntity);
    }

    @Transactional
    public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTO){
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        pedidoEntity.setNomeCliente(pedidoDTO.nomeCliente());
        pedidoEntity.setDataPedido(pedidoDTO.dataPedido());

        pedidoEntity.getItens().clear();

        List<PedidoItemEntity> itensEntidade =
            pedidoDTO.itens().stream()
                    .map(itemRequest -> criarPedidoItem(itemRequest, pedidoEntity))
                    .toList();

        pedidoEntity.getItens().addAll(itensEntidade);

        BigDecimal valorTotal = somarValorTotal(itensEntidade);
        pedidoEntity.setValorTotal(valorTotal);


        PedidoEntity pedidoSalvo = pedidoRepository.save(pedidoEntity);
        return pedidoMapper.toDTO(pedidoSalvo);
    }
}
