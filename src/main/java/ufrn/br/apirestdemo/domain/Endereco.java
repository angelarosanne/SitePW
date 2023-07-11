package ufrn.br.apirestdemo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ufrn.br.apirestdemo.controller.EnderecoController;
import ufrn.br.apirestdemo.controller.PessoaController;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco extends AbstractEntity {
    String rua;
    String numero;
    String bairro;
    String cidade;

     @Override
    public void partialUpdate(AbstractEntity e) {

    }

    @Data
    public static class DtoRequest{
        @NotBlank(message = "Usuário com cidade em branco")
        String cidade;
        @NotBlank(message = "Usuário com rua em branco")
        String rua;
        @NotBlank(message = "Usuário com bairro em branco")
        String bairro;
        @NotBlank(message = "Usuário com numero em branco")
        String numero;


        public static Endereco convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Endereco.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
            String rua;
            String numero;
            String bairro;
            String cidade;

        public static DtoResponse convertToDto(Endereco e, ModelMapper mapper){
            return mapper.map(e, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(EnderecoController.class).slash(id).withRel("editar"));
        }

    }
}
