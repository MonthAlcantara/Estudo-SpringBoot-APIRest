package io.github.monthalcantara.mercadolivre.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/*
 * Essa classe será usada para filtrar as requisições pegando o token
 * para validar. Ela estende OncePerRequestFilter que faz esse serviço
 * uma vez por requisiçãp
 * */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /*
     * Essa é a classe que lida com o JWT questão da secret e do tempo de expiração
     * estão lá
     * */
    private TokenManager tokenManager;
    private UsersService usersService;

    public JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
        this.tokenManager = tokenManager;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        /*
         * Tentando pegar um token do request getTokenFromRequest(request)
         * */
        Optional<String> possibleToken = getTokenFromRequest(request);

        /*
         * Se não tem um token no Header, a lógica segue normal sem colocar o usuario na memória
         * Se tem o token, eu verifico se o token é válido (Se respeita o secret configurado e
         * o prazo de expiração tambem configurado no properties e no managerToken
         * */
        if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {

            /*
             * Pego o username contido no token através do tokenManager
             * */
            String userName = tokenManager.getUserName(possibleToken.get());

            /*
             * Carrego o Usuario daquele username contido no token e passo para
             * o Spring Secury no formato de UserDetails
             * */
            UserDetails userDetails = usersService.loadUserByUsername(userName);

            /*
             * Criando um authentication token pra dentro do spring. Essa é uma abstração do
             * Spring Security onde eu digo ao Spring que estou authenticando alguém, basta
             * instanciar o new UsernamePasswordAuthenticationToken nesse caso nem precisa que
             * eu informe a senha
             * */
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            /*
             * Busco o contexto do Spring e seto na mão a authenticação do usuario acima
             * (Nesse caso nem está sendo usado a parte de Roles. Estou pegando apenas se
             * o token é válido e se tem um username válido...authenticando esse usuario
             * com o UsernamePasswordAuthenticationToken e setando ele na mão no contexto
             * do spring Security
             * */
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    /*
     * Buscando o token pegando o valor do atributo Authorization que vem no Header da
     * requisição
     * */
    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        /*
         * Pelo request eu consigo pegar os dados da requisição inclusive os dados do header
         * eu chamo o método getHeader(Passando o nome do que eu quero no header) eu consigo
         * buscar os valores
         * */
        String authToken = request.getHeader("Authorization");

        return Optional.ofNullable(authToken);
    }
}
