package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
/*
 * Essa classe só será carregada quando o perfil ativo for o de Dev.
 * Eu preciso informar ao Springo qual perfil deve estar ativo, se eu não
 * informar, o Spring subirá um perfil default dele, ou seja vai carregar
 * todas as classes que não esteja anotada com @Profile
 * */
@Profile("dev")
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*
     * Esse método que recebe um WebSecurity serve para configurar a parte de autorização
     * urls, permissões de acesso, perfis de acesso e etc
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * No ambiente de dev eu não quero ter segurança. QUero permitir todas as requisições
         * */
        http.authorizeRequests()
                //Permito acesso a todos
                .antMatchers("/**").permitAll()
                .and().csrf().disable();

    }

}