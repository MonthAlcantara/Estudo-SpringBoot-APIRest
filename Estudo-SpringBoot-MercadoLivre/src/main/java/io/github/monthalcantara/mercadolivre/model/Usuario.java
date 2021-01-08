package io.github.monthalcantara.mercadolivre.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
/*
* Indices devem ser criados em tabela que recebem muitas consultas mas que os dados são
* pouco atualizados por que isso força o indice a criar uma nova métrica a cada atualização
* iae o ganho de performance por ter o indice pode ser um tiro no pé
* */
@Table(name = "usuario", indexes = {@Index(name = "login", unique = true, columnList = "login")})
@NamedQuery(name = "Usuario.BUSCA_POR_LOGIN", query = "select u from Usuario u where u.login = :username")
public class Usuario {

    //Criando uma variável statica para poder usar em qqr outra classe e não precisar reescrever a query
    public static final String BUSCA_POR_LOGIN = "Usuario.BUSCA_POR_LOGIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    private LocalDateTime instanteCriacao;

    @OneToMany(mappedBy = "usuario")
    private Set<Opiniao> opinioes = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<Pergunta> perguntas = new HashSet<>();


    @Deprecated
    public Usuario() {
    }

    /**
     * @param login String formato de Email
     * @param senha String não encodado (Texto Limpo)
     */
    public Usuario(@NotBlank @Email String login, @NotBlank @Size(min = 6) String senha) {
        verificaDadosDeEntrada(login, senha);
        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.instanteCriacao = LocalDateTime.now();
    }

    private void verificaDadosDeEntrada(@NotBlank @Email String login, @NotBlank @Size(min = 6) String senha) {
        Assert.hasText(login, "Login do usuario não pode ser vazio");
        Assert.hasText(senha, "Login do usuario não pode ser vazio");
        Assert.isTrue(senha.length() >= 6, "A senha do usuario precisa ter 6 mais caracteres");
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public void adicionarOpiniao(Opiniao opiniao) {
        this.opinioes.add(opiniao);
    }

    public Set<Opiniao> getOpinioes() {
        return Collections.unmodifiableSet(opinioes);
    }

    public Set<Pergunta> getPerguntas() {
        return Collections.unmodifiableSet(perguntas);
    }
}
