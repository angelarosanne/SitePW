package ufrn.br.apirestdemo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ufrn.br.apirestdemo.domain.Endereco;
import ufrn.br.apirestdemo.domain.Pessoa;
import ufrn.br.apirestdemo.service.EnderecoService;
import ufrn.br.apirestdemo.service.PessoaService;

import java.util.List;


@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    EnderecoService service;
    ModelMapper mapper;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco.DtoResponse create(@RequestBody Endereco.DtoRequest p){

        Endereco endereco = this.service.create(Endereco.DtoRequest.convertToEntity(p, mapper));

        Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(endereco, mapper);
        response.generateLinks(endereco.getId());

        return response;
    }



//    @GetMapping
//    public List<Endereco.DtoResponse> list(){
//
//        return this.service.list().stream().map(
//                elementoAtual -> {
//                    Endereco.DtoResponse response = Endereco.DtoResponse.convertToDto(elementoAtual, mapper);
//                    response.generateLinks(elementoAtual.getId());
//                    return response;
//                }).toList();
//    }
}
