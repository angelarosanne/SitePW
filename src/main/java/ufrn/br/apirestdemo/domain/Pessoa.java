package ufrn.br.apirestdemo.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import ufrn.br.apirestdemo.controller.PessoaController;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE pessoa SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Pessoa  extends AbstractEntity{

    String nome;
    Integer idade;
    Boolean admin = false;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "endereco_id")
	Endereco endereco;


    @Data
    public static class DtoRequest{
        @NotBlank(message = "Usuário com nome em branco")
        String nome;
        @NotBlank(message = "Usuário com endereco em branco")
        Endereco endereco;
        @Min(value = 18, message = "Usuário com idade insuficiente")
        Integer idade;


        public static Pessoa convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Pessoa.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String nome;
        Integer idade;
        Endereco endereco;

        public static DtoResponse convertToDto(Pessoa p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PessoaController.class).slash(id).withRel("delete"));
        }

    }


}