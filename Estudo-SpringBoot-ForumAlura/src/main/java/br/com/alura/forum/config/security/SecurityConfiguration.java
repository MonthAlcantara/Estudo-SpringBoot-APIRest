package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    /*
     *  Esse método que recebe um AuthenticationManagerBuilder serve para configurar a parte de autenticação
     * Controle de acesso, login e etc
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                /*Qual a classe de Serviço que tem a lógica que faz a authenticação?
                  Para isso foi criada a Classe AutenticaçãoService e fiz ela implementar UserDetailsService
                * */
                .userDetailsService(autenticacaoService)
                //Qual foi o algoritimo de Hash usado para salvar a senha no banco?
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /*
     * Esse método que recebe um WebSecurity serve para configurar a parte de autorização
     * urls, permissões de acesso, perfis de acesso e etc
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Quais requests serão autorizadas e como será a autorização
                .authorizeRequests()
                /*Quais Urls eu vou filtrar? É pra permitir ou bloquear?
                O antMatchers tem 3 sobrecargas
                 1 - Passando só a url,
                 2 - Passando só p método (Get, Post...etc)
                 3 - Passando método + url
                 Nesse caso liberei o /topicos o /topicos/ com barra no final é barrado
                */
                .antMatchers(HttpMethod.GET, "/topicos")
                //Permito acesso a todos
                .permitAll()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                //Qualquer outra requisção fora as que eu citei acima
                .anyRequest()
                //Só aceite requisição de pessoas authenticadas
                .authenticated()
                // e
                .and()
                //E utilize o formulario de Login padrão do spring pra fazer a autenticação
                .formLogin();

    }

    /*
     * Serve para configurar o acesso a conteúdos estáticos como CSS, JS, Imagens
     * Faria sentido numa aplicação com Spring MVC, nesse caso de Api com apenas Backend, não faz muito sentido
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}