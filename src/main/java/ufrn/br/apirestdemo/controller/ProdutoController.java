package ufrn.br.apirestdemo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ufrn.br.apirestdemo.domain.Pessoa;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.service.PessoaService;
import ufrn.br.apirestdemo.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "X-Total-Count")
public class ProdutoController {

    ProdutoService service;
    ModelMapper mapper;

    public ProdutoController(ProdutoService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto.DtoResponse create(@RequestBody Produto.DtoRequest p){

        Produto produto = this.service.create(Produto.DtoRequest.convertToEntity(p, mapper));

        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());

        return response;
    }



    @GetMapping
    public List<Produto.DtoResponse> list(){

        return this.service.list().stream().map(
                elementoAtual -> {
                    Produto.DtoResponse response = Produto.DtoResponse.convertToDto(elementoAtual, mapper);
                    response.generateLinks(elementoAtual.getId());
                    return response;
                }).toList();
    }
    
}
