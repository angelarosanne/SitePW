package ufrn.br.apirestdemo.repository;

import java.util.Optional;

import ufrn.br.apirestdemo.domain.Pessoa;

public interface PessoaRepository extends IGenericRepository<Pessoa> {
    Optional<Pessoa> findPessoaByLogin(String login);
}
