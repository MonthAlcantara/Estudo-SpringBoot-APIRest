package io.github.monthalcantara.mercadolivre.compartilhado;

import io.github.monthalcantara.mercadolivre.configuration.security.UserDetailsMapper;
import io.github.monthalcantara.mercadolivre.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component // Pra que ele possa ser injetado
public class AppUserDetailsMapper implements UserDetailsMapper {

    @Override

    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario) shouldBeASystemUser);
    }
}
