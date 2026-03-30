package meu.estudo.pedindofacil.controller;

import meu.estudo.pedindofacil.dto.PedidoRequest;
import meu.estudo.pedindofacil.dto.PedidoResponse;
import meu.estudo.pedindofacil.dto.ProdutoResponse;
import meu.estudo.pedindofacil.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> salvar(@RequestBody PedidoRequest pedidoRequest) {
        PedidoResponse pedidoResponse = pedidoService.salvar(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponse);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> listarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizar(
        @RequestBody PedidoRequest pedidoRequest,
        @PathVariable Long id
    ) {
        PedidoResponse pedidoResponse = pedidoService.atualizar(id, pedidoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoResponse);
    }
}

