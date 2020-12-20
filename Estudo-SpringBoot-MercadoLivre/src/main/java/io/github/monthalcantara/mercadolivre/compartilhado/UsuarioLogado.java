package io.github.monthalcantara.mercadolivre.compartilhado;

import io.github.monthalcantara.mercadolivre.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/*
* Essa implementação foi criada separada para não encher de métodos
* a minha classe que representa o usuário mas o Usuario poderia implementar
* o User Details
* */
public class UsuarioLogado implements UserDetails {
    private Usuario usuario;
    private User springUserDetails;

    public UsuarioLogado(@NotNull @Valid Usuario usuario) {
        this.usuario = usuario;
        springUserDetails = new User(usuario.getLogin(), usuario.getSenha(), List.of());
    }



    public Collection<GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }

    public String getPassword() {
        return springUserDetails.getPassword();
    }

    public String getUsername() {
        return springUserDetails.getUsername();
    }

    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }

    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }

    public Usuario get() {
        return usuario;
    }

}
