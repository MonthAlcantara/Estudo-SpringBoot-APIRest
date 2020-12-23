package br.com.alura.forum.modelo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Implementando o UserDetails eu digo ao Spring que essa classe
 * é a classe que representa o Usuario que ele deve autenticar
 * */
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    //Como eu sempre vou precisar trazer os perfis ao buscar o usuario. Eu ja seto o fetch como eager
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /*
     * Com esse método eu digo qual Role do usuario, qual a Claim
     * Qual o perfil de autorização que ele possui
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    //Esses métodos serão usados pelo spring para fazer a autenticação

    /*
     * Qual atributo da minha classe que representa o Password?
     * */
    @Override
    public String getPassword() {
        return this.senha;
    }

    /*
     * Qual atributo da minha classe que representa o Username?
     * */
    @Override
    public String getUsername() {
        return this.email;
    }

    /*
     * Se houvesse um controlle maior na minha aplicação eu poderia usar
     * esse métodos de controle sobre a conta (Não é o caso nessa aplicação aqui)
     * Nesse projeto está sendo assumido que qqr conta não expira nem é bloqueada
     * */

    // A conta não esta expirada?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // A conta não esta bloqueada?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // A credencial da conta não esta expirada?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // A conta não esta Habilitada?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
