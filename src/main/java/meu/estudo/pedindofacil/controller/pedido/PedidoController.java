package meu.estudo.pedindofacil.controller.pedido;

import meu.estudo.pedindofacil.dto.pedido.PedidoDTO;
import meu.estudo.pedindofacil.service.pedido.PedidoService;
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
    public ResponseEntity<PedidoDTO> salvar(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedido = pedidoService.salvar(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizar(
        @RequestBody PedidoDTO pedidoDTO,
        @PathVariable Long id
    ) {
        PedidoDTO pedido = pedidoService.atualizar(id, pedidoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }
}

