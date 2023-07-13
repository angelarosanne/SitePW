package ufrn.br.apirestdemo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ufrn.br.apirestdemo.domain.Pessoa;
import ufrn.br.apirestdemo.domain.Produto;
import ufrn.br.apirestdemo.service.PessoaService;
import ufrn.br.apirestdemo.service.ProdutoService;

import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/produtos/")
@CrossOrigin(origins = "http://localhost:3000")
public class ProdutoController {

    ProdutoService service;
    ModelMapper mapper;

    public ProdutoController(ProdutoService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto.DtoResponse create(@RequestBody @Valid Produto.DtoRequest p){

        Produto produto = this.service.create(Produto.DtoRequest.convertToEntity(p, mapper));

        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());

        return response;
    }



    // @GetMapping
    // public List<Produto.DtoResponse> list(){

    //     return this.service.list().stream().map(
    //             elementoAtual -> {
    //                 Produto.DtoResponse response = Produto.DtoResponse.convertToDto(elementoAtual, mapper);
    //                 response.generateLinks(elementoAtual.getId());
    //                 return response;
    //             }).toList();
    // }
    

    @GetMapping
    public ResponseEntity<Page<Produto.DtoResponse>> find(Pageable page) {

        //System.out.println(page.toString());

        Page<Produto.DtoResponse> dtoResponses = service
                .find(page)
                .map(record -> {
                    Produto.DtoResponse response = Produto.DtoResponse.convertToDto(record, mapper);
                    response.generateLinks(record.getId());
                    return response;
                });


        return new ResponseEntity<>(dtoResponses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Produto.DtoResponse getById(@PathVariable Long id){

        Produto produto = this.service.getById(id);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(produto, mapper);
        response.generateLinks(produto.getId());

        return response;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }


    @PutMapping("{id}")
    public Produto.DtoResponse update(@RequestBody Produto.DtoRequest dtoRequest, @PathVariable Long id){
        Produto p = Produto.DtoRequest.convertToEntity(dtoRequest, mapper);
        Produto.DtoResponse response = Produto.DtoResponse.convertToDto(this.service.update(p, id), mapper);
        response.generateLinks(id);
        return response;
    }
}
