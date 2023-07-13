package ufrn.br.apirestdemo.repository;

import org.springframework.data.repository.ListCrudRepository;
import ufrn.br.apirestdemo.domain.AbstractEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long>, PagingAndSortingRepository<E, Long> {
}

