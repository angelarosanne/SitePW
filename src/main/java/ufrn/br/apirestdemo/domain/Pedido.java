package ufrn.br.apirestdemo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import ufrn.br.apirestdemo.controller.PedidoController;
import ufrn.br.apirestdemo.controller.PessoaController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SQLDelete(sql = "UPDATE pessoa SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Pedido extends AbstractEntity {
	Date dataPedido;
    @ManyToOne
	@JoinColumn(name = "pessoa_id")
	Pessoa pessoa;

    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "pedido_produto",
			joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id")
	)
	List<Produto> produtos;
    //List<Long> produto_id;

    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Pedido pedido){
            this.dataPedido = pedido.dataPedido;
            this.pessoa = pedido.pessoa;
            this.produtos = pedido.produtos;
        }
    }

    @Data
    public static class DtoRequest{
        @NotBlank(message = "Usuário com dataPedido em branco")
        Date dataPedido;

        @NotBlank(message = "Obrigatório ter uma pessoa")
        Pessoa pessoa;

        @NotBlank(message = "Obrigatório ter produtos")
         List<Long> produto_id;

        public static Pedido convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Pedido.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
	    Date dataPedido;
        Pessoa pessoa;
        List<Produto> produtos;
        public static DtoResponse convertToDto(Pedido p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PedidoController.class).withRel("pedidos"));
            add(linkTo(PedidoController.class).slash(id).withRel("delete"));
        }

    }

}
