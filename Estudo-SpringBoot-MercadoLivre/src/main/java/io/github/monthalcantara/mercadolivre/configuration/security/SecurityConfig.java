package io.github.monthalcantara.mercadolivre.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration // Por ser uma Classe de configuração a ser lida pelo @SpringBootApplication
@EnableWebSecurity // Para que essa classe se integre ao SpringMVC
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenManager tokenManager;


    private static final Logger log = LoggerFactory
            .getLogger(SecurityConfig.class);


    @Override
    /*
    * Esse bean está sendo usado com uma constante do próprio Spring Security
    * que ja define como o Authentication Manager é exposto no contexto do Spring
    * */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                /*
                 * Requisições do tipo Get para o endereço /produtos
                 * passando um id que é um numero que pode ser um digito ou +
                 * permita todos os acessos
                 */
                .antMatchers(HttpMethod.GET, "/produtos/{id:[0-9]+}").permitAll()
                /*
                 * Ao receber um post para o endpoint /usuarios, permita todos
                 * os acessos
                 */
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                /*
                 * Meu endpoint de autenticação é o /api/auth/** ,
                 * permita todos os acessos. Esse é um endpoint customizado por que
                 * depois de authenticado eu quero devolver um Token iae o default
                 * do Spring security é ter um filtro de autenticação que coloca na sessão
                 * iae eu teria que fazer uma configuração a mais nesse filtro que ja existe
                  além do filtro que eu ja coloquei na aplicação UsernamePasswordAuthenticationFilter
                  que verifica se a pessoa está logada ou não
                 */
                .antMatchers("/api/auth/**").permitAll()
                /*
                 * Para todas as demais requests, exija autenticação
                 */
                .anyRequest().authenticated()
                /*
                 * Habilita o Cors
                 */
                .and()
                .cors()
                /*
                 * Desabilita o crfs que é uma proteção contra um tipo de ataque
                 * Não faz sentido essa proteção para API pq em API's os estados
                 * do lado do servidor (por isso geralmente não se utiliza esse
                 * tipo de proteção
                 */
                .and()
                .csrf().disable()
                .sessionManagement()
                /*
                 * Essa config diz que eu nunca quero criar uma sessão no servidor
                 * Por que minha autenticação é via Token e a cada requisição será
                 * informado um token para autenticação, não sendo nescessario salvar
                 * isso em sessão
                 */
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /*
                 * Essa config diz que antes de passar pelo filtro de autenticação
                 * do Spring Security eu preciso carregar o token que veio na
                 * requisição pelo Header colocar um Usuario na memporia e fingir
                 * pro Spring Security que aquele usuário foi autenticado de
                 * maneira regular
                 */
                .addFilterBefore(new JwtAuthenticationFilter(tokenManager, usersService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());

    }

    /*
    * Nesse ponto eu digo que estou usando o Bcrypt para encodar minha senha
    * O mesmo utilizado na senha para salvar no banco
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usersService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html",
                "/v2/api-docs",
                "/webjars/**",
                "/configuration/**",
                "/swagger-resources/**",
                "/css/**",
                "/**.ico",
                "/js/**");
    }

    private static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {

            logger.error("Um acesso não autorizado foi verificado. Mensagem: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Você não está autorizado a acessar esse recurso.");
        }
    }
}
