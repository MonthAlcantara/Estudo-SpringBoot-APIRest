package io.github.monthalcantara.restspringbootestudo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
 /*                                           localhost:8080/person.x-yaml
     configurer
                .favorParameter(false) // Para habilitar ou desabilitar que a escolha do formato seja feita por parametro
                .ignoreAcceptHeader(false) // não vai ignorar o que vier no cabeçalho da requisição
                .defaultContentType(MediaType.APPLICATION_JSON) // O conteúdo padrão será o Json
                .mediaType("json", MediaType.APPLICATION_JSON) // Além disso ele irá suportar os seguintes MediaType...
                .mediaType("xml", MediaType.APPLICATION_XML);
     */

        /*                                  localhost:8080/person?mediaType=xml
        configurer
                .favorPathExtension(false) // Para habilitar ou desabilitar que a escolha do formato seja feita por variável
                .favorParameter(true) // Para habilitar ou desabilitar que a escolha do formato seja feita por parametro
                .parameterName("mediaType") // Nome do parametro que irá receber o tipo de arquivo
                .ignoreAcceptHeader(true) // vai ignorar o que vier no cabeçalho da requisição
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
        */
        /* Pelo Header. Key = Accept  e Value = application/extensao */
        configurer
                .favorPathExtension(false)
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);

    }
}