package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
/*
 * Carrega apenas o contexto de persistencia
 * */
@DataJpaTest
/*
* Por padrão o Spring assume que será utilizado um bando de dados em memória para
* realizar o teste. Se eu não tiver uma dependencia de um bd em memória no meu
* pom.xml o Spring irá acusar erro. Para mudar esse comportamento padrão eu
* posso utilizar a annotation abaixo informando ao sprig para não (NONE) fazer
* a substituição (REPLACE) do meu banco de dados e usar o mesmo que utilizo na minha aplicação
*
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
**/
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EntityManager manager;
    /*
     * TestEntityManager fornece um subconjunto de métodos de EntityManager que são úteis para testes,
     *  bem como métodos de ajuda para tarefas de teste comuns, como persiste ou findar
     * */
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria carregar um curso ao buscar pelo nome com EntityManager")
    public void findByNomeComEntityManager() {

        Curso cursoPreSalvo = new Curso();
        cursoPreSalvo.setNome("HTML 5");

        em.persist(cursoPreSalvo);

        String cursoNome = "HTML 5";

        Curso curso = (Curso) manager.createQuery("select c from Curso c where c.nome =:nome")
                .setParameter("nome", cursoNome)
                .getSingleResult();
        Assert.assertNotNull(curso);
        Assert.assertEquals(cursoNome, curso.getNome());
    }

    @Test
    @DisplayName("Deveria carregar um curso ao buscar pelo nome com Repository")
    public void findByNomeComReepository() {
        Curso cursoPreSalvo = new Curso();
        cursoPreSalvo.setNome("HTML 5");

        em.persist(cursoPreSalvo);

        String cursoNome = "HTML 5";
        Curso curso = cursoRepository.findByNome(cursoNome);
        Assert.assertNotNull(curso);
        Assert.assertEquals(cursoNome, curso.getNome());
    }

    @Test
    @DisplayName("Não deveria carregar um curso que não existe no banco ao chamar por nome")
    public void findByNomeFail() {
        String cursoNome = "Autocad";
        Curso curso = cursoRepository.findByNome(cursoNome);

        Assert.assertNull(curso);
    }
}
