package ufrn.br.apirestdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufrn.br.apirestdemo.controller.PessoaController;
import ufrn.br.apirestdemo.controller.ProdutoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Produto extends AbstractEntity {

	String nome;
    String preco;



    @Data
    public static class DtoRequest{
        @NotBlank(message = "Produto com nome em branco")
        String nome;

        @NotBlank(message = "Produto com preco em branco")
        String preco;

        public static Produto convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Produto.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String nome;
        String preco;
        public static DtoResponse convertToDto(Produto p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(ProdutoController.class).slash(id).withSelfRel());
            add(linkTo(ProdutoController.class).withRel("produtos"));
            add(linkTo(ProdutoController.class).slash(id).withRel("delete"));
        }

    }

	

}