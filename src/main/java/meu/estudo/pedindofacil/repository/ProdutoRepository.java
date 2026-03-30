package meu.estudo.pedindofacil.repository;
import meu.estudo.pedindofacil.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {}
