package br.com.alura.forum.modelo;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Como o método getAuthorities do UserDetails Espera uma Coleção de GrantedAuthority
 * e não de Perfil, eu implemento dessa classe fazendo perfil um GrantedAuthority
 * */
@Entity
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Perfil(String nome) {
        this.nome = nome;
    }

    /*
     * Único método do GrantedAuthority que retorna qual seria o autority
     * nesse caso seria o nome da minha classe Perfil, então retorno ele
     * */
    @Override
    public String getAuthority() {
        return this.nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
