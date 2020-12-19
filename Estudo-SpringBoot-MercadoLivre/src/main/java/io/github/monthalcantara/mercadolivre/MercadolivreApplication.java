package io.github.monthalcantara.mercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MercadolivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadolivreApplication.class, args);
	}

}
