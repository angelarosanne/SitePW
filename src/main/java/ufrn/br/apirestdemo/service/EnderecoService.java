package ufrn.br.apirestdemo.service;

import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.domain.Endereco;
import ufrn.br.apirestdemo.repository.EnderecoRepository;
import ufrn.br.apirestdemo.repository.IGenericRepository;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class EnderecoService extends GenericService<Endereco, EnderecoRepository> {

    public EnderecoService(EnderecoRepository repository) {
        super(repository);
    }
}