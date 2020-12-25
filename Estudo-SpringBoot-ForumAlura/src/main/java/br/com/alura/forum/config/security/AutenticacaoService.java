package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * O auth.userDetailsService espera um UserDetailsService e não um AutenticacaoService
 * Para dizer ao Spring que essa é a classe que possui a lógica de autenticacao eu preciso
 * implementar UserDetailsService
 * */
@Service //Por ser uma Service e eu precisar injetala...eu anoto como @Service
public class AutenticacaoService implements UserDetailsService {

    private UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /*
     * La na tela do login (A do Spring ou outra qqr) quan eu digitar a senha e o login e dar enter,
     * O Spring vai vim nessa classe e vai buscar esse método loadUserByUsername, foi isso que eu
     * defini no configure(AuthenticationManagerBuilder auth)
     * o login usado na tela é o parametro recebido pelo método
     * A senha é checada em memória pelo Spring
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(username);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados inválidos");
    }
}
