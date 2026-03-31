package meu.estudo.pedindofacil.controller.produto;

import meu.estudo.pedindofacil.dto.produto.ProdutoDTO;
import meu.estudo.pedindofacil.service.produto.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@RequestBody ProdutoDTO produto) {
        ProdutoDTO produtoDTO = produtoService.salvar(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(
        @RequestBody ProdutoDTO produto,
        @PathVariable Long id
    ) {
        ProdutoDTO produtoDTO = produtoService.atualizar(id, produto);

        return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
    }
}
