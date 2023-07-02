package ufrn.br.apirestdemo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.br.apirestdemo.domain.Endereco;
import ufrn.br.apirestdemo.domain.Pedido;
import ufrn.br.apirestdemo.domain.Pessoa;
import ufrn.br.apirestdemo.repository.EnderecoRepository;
import ufrn.br.apirestdemo.repository.IGenericRepository;
import ufrn.br.apirestdemo.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService extends GenericService<Pedido, PedidoRepository> {

    public PedidoService(PedidoRepository repository) {
        super(repository);
    }
}