package ufrn.br.apirestdemo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ufrn.br.apirestdemo.domain.Pedido;
import ufrn.br.apirestdemo.domain.Pessoa;
import ufrn.br.apirestdemo.service.PedidoService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    PedidoService service;
    ModelMapper mapper;


    public PedidoController(PedidoService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping
    public List<Pedido.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido.DtoResponse create(@RequestBody Pedido.DtoRequest p){

        Pedido pedido = (Pedido) this.service.create(Pedido.DtoRequest.convertToEntity(p, mapper));

        Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
        response.generateLinks(pedido.getId());

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido.DtoResponse> getPedido(@PathVariable Long id) {
        Pedido pedido = (Pedido) service.getById(id);
        if (pedido != null) {
            Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(pedido, mapper);
            response.generateLinks(pedido.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido.DtoResponse> updatePedido(@PathVariable Long id, @Valid @RequestBody Pedido.DtoRequest dtoRequest) {
        Pedido existingPedido = (Pedido) service.getById(id);
        if (existingPedido != null) {
            mapper.map(dtoRequest, existingPedido);
            Pedido updatedPedido = (Pedido) service.update(existingPedido, id);
            Pedido.DtoResponse response = Pedido.DtoResponse.convertToDto(updatedPedido, mapper);
            response.generateLinks(updatedPedido.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
