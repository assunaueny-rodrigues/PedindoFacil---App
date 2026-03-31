package meu.estudo.pedindofacil.repository.produto;
import meu.estudo.pedindofacil.entity.produto.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {}
