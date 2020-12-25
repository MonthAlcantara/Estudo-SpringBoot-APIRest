package io.github.monthalcantara.mercadolivre.configuration.security;

import io.github.monthalcantara.mercadolivre.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/*
 * UserDetailsService é um interface do Spring Security
 * Elá é importante por que é ela que é implementada para
 * dado um Username encontrado no token, eu preciso saber
 * se existe alguem correspondente no meu banco de dados
 * */
@Service
public class UsersService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    /*
     * Esse atributo se refere a uma query definida no application properties
     * */
    @Value("${security.username-query}")
    private String query;
    @Autowired
    private UserDetailsMapper userDetailsMapper;


    /*
     * Nesse código em específico não está sendo considerado o password
     * Apenas o username é usado
     * */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        /*
         * Query definida no application properties security.username-query
         * */
        List<Usuario> objects = manager.createNamedQuery(Usuario.BUSCA_POR_LOGIN,Usuario.class)
                .setParameter("username", username).getResultList();
//        List<?> objects = manager.createQuery(query)
//                .setParameter("username", username).getResultList();
        /*
         * Programação defensiva. Se tiver mais de um usuario com o mesmo login
         * é bug (Não deveria existir)
         * */
        Assert.isTrue(objects.size() <= 1,
                "[BUG] mais de um autenticável tem o mesmo username. "
                        + username);

        /*
         * Caso não seja encontrado ninguem com esse username então é um
         *  UsernameNotFoundException
         * */
        if (objects.isEmpty()) {
            throw new UsernameNotFoundException(
                    "Não foi possível encontrar usuário com email: "
                            + username);
        }
        /*
         * Se deu tudo certo eu preciso retornar um UserDetails
         * Para isso foi definida uma interface chamada userDetailsMapper
         * que é implementado por AppUserDetailsMapper
         * */
        return userDetailsMapper.map(objects.get(0));
    }

}
