package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Essa classe será usada para filtrar as requisições pegando o token
* para validar. Ela estende OncePerRequestFilter que faz esse serviço
* uma vez por requisiçãp
* */
@Configuration
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Aqui eu preciso fazer a lógica para capturar, validar e informar pra o Spring que o token é valido

        String token = recuperarToken(httpServletRequest);
        /*
        Aqui eu digo que ja fiz o que eu queria fazer e agora o fluxo (Que esse filtro interceptou)
        ja pode seguir pra onde ele estava indo
        */
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        //Próximo passo é validar o token iae vou usar novamente a biblioteca jjwt
    }

    private String recuperarToken(HttpServletRequest httpServletRequest) {
    /*
    * Pelo request eu consigo pegar os dados da requisição inclusive os dados do header
    * eu chamo o método getHeader(Passando o nome do que eu quero no header) eu consigo
    * buscar os valores
    * */
        String token = httpServletRequest.getHeader("Authorization");
        /*
        * Se nulo, vazio ou se não começar com 'Bearer ', retorne null
        * */
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        /*
        * Retorne o token menos os 7 primeiros caracteres que seriam Bearer + o espaço
        * */
        return token.substring(7, token.length());

    }
}
