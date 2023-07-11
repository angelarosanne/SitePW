package ufrn.br.apirestdemo.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ufrn.br.apirestdemo.controller.PessoaController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE pessoa SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Pessoa  extends AbstractEntity implements UserDetails{

    String nome;
    Integer idade;
    Boolean admin = false;
    String login;
    String senha;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "endereco_id")
	Endereco endereco;


    @Override
    public void partialUpdate(AbstractEntity e) {
        if (e instanceof Pessoa pessoa){
            this.nome = pessoa.nome;
            this.idade = pessoa.idade;
            this.login = pessoa.login;
            this.senha = pessoa.senha;
        }
    }

    @Data
    public static class DtoRequest{
        @NotBlank(message = "Usuário com nome em branco")
        String nome;
        @NotBlank(message = "Usuário com endereco em branco")
        Endereco endereco;
        @Min(value = 18, message = "Usuário com idade insuficiente")
        Integer idade;

        String login;

        String senha;
        


        public static Pessoa convertToEntity(DtoRequest dto, ModelMapper mapper){
            return mapper.map(dto, Pessoa.class);
        }
    }

    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        String nome;
        Integer idade;
        Endereco endereco;
        String login;
        String senha;

        public static DtoResponse convertToDto(Pessoa p, ModelMapper mapper){
            return mapper.map(p, DtoResponse.class);
        }

        public void generateLinks(Long id){
            add(linkTo(PessoaController.class).slash(id).withRel("delete"));
        }

    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Adicione as autoridades associadas ao usuário
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Adicione mais autoridades conforme necessário

        return authorities;
}


}
