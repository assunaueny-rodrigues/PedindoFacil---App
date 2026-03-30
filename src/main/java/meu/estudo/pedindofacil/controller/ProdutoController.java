package meu.estudo.pedindofacil.controller;

import meu.estudo.pedindofacil.dto.ProdutoRequest;
import meu.estudo.pedindofacil.dto.ProdutoResponse;
import meu.estudo.pedindofacil.service.ProdutoService;
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
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody ProdutoRequest produtoRequest) {
        ProdutoResponse produtoResponse = produtoService.salvar(produtoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
        @RequestBody ProdutoRequest produtoRequest,
        @PathVariable Long id
    ) {
        ProdutoResponse produtoResponse = produtoService.atualizar(id, produtoRequest);

        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        ProdutoResponse produtoResponse = produtoService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);
    }
}
