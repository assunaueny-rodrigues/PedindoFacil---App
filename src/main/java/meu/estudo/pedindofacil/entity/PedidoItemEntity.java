package meu.estudo.pedindofacil.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
