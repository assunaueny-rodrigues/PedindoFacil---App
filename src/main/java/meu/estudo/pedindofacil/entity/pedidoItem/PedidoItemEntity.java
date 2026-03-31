package meu.estudo.pedindofacil.entity.pedidoItem;

import jakarta.persistence.*;
import lombok.*;
import meu.estudo.pedindofacil.entity.pedido.PedidoEntity;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"produto", "pedido"})
@Table(name = "pedido_item")
public class PedidoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

    private Integer quantidade;

    private BigDecimal valorTotal;
}
