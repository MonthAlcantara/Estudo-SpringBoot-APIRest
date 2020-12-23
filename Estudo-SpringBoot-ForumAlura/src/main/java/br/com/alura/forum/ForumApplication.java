package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
/*
* Cache geralmente é usado para melhorar a performance da aplicação. Porém colocar cache em tabelas que
* constantemente são atualizadas podem acabar degradando a performance. Cache deve ser usado em tabelas
* que raramente são atualizadas
* */
@EnableCaching
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
