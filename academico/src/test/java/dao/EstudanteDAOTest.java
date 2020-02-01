package dao;


import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import dao.CursoDAO;
import dao.DepartamentoDAO;
import dao.DisciplinaDAO;
import dao.EstudanteDAO;
import dao.UniversidadeDAO;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class EstudanteDAOTest {
	
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Curso.class)
            .addEntityClass(Disciplina.class)
            .addEntityClass(Departamento.class)
            .addEntityClass(Universidade.class)
            .addEntityClass(Estudante.class)
            .addEntityClass(Professor.class)
            .addEntityClass(Estudante.class).build();
    
    
    private DepartamentoDAO daoDepartamento;
    private UniversidadeDAO daoUniversidade;
    private CursoDAO daoCurso;
    private DisciplinaDAO daoDisciplina;
    private EstudanteDAO daoEstudante;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("EstudanteDAO - setUp ");
        daoUniversidade = new UniversidadeDAO(dbTesting.getSessionFactory());
        daoDepartamento = new DepartamentoDAO(dbTesting.getSessionFactory());
        daoCurso = new CursoDAO(dbTesting.getSessionFactory());
        daoDisciplina = new DisciplinaDAO(dbTesting.getSessionFactory());
        daoEstudante = new EstudanteDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("EstudanteDAO - test create");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
    	
        Estudante e = new Estudante("Ailton", c_saved);  
        Estudante e_saved = dbTesting.inTransaction(() -> daoEstudante.persist(e));

        assertNotNull(e_saved);
        
        assertAll(
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Estudante(null, c_saved); }),
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Estudante("", c_saved); }),
        			() -> assertThrows(NullPointerException.class, () -> { new Estudante("aaa", null); })
        		);
 
        assertAll(
        			() -> assertNotNull(e_saved.getId()),
        			() -> assertEquals("Ailton", e_saved.getNome())
        		);
        
        
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        List<Long> pre_req = new ArrayList<Long>();
        
        assertAll(
        			() -> assertEquals(e.getId(), e_saved.getId()),
        			() -> assertEquals(e.getNome(), e_saved.getNome()),
        			() -> assertEquals(0, e_saved.getScore()),
        			() -> assertTrue( disciplinas.equals( e_saved.getDisciplinas() )),
        			() -> assertTrue( pre_req.equals( e_saved.getPre_disciplinas() ))
        		);
        
        
        e_saved.setId( (long) 1);
        e_saved.setNome("Alan");
        e_saved.setScore((long) 10);
        
        assertAll(
        			() -> assertEquals(1, e_saved.getId()),
        			() -> assertEquals("Alan", e_saved.getNome()),
        			() -> assertEquals(10, e_saved.getScore())
        		);
    }
    
    @Test
    public void testRead() {

        System.out.println("EstudanteDAO - test read");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
    	
        Estudante e = new Estudante("Ailton", c_saved);  
        Estudante e_saved = dbTesting.inTransaction(() -> daoEstudante.persist(e));

        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        List<Long> pre_req = new ArrayList<Long>();
        
        assertAll(
        			() -> assertEquals(e.getNome(), e_saved.getNome()),
        			() -> assertEquals( e.getId(), e_saved.getId()),
        			() -> assertEquals(0, e_saved.getScore()),
        			() -> assertTrue( disciplinas.equals( e_saved.getDisciplinas() )),
        			() -> assertTrue( pre_req.equals( e_saved.getPre_disciplinas() ))
        		);
        
        
        Estudante e2 = new Estudante("Ana", c_saved);  
        Estudante e_saved2 = dbTesting.inTransaction(() -> daoEstudante.persist(e2));
    
        assertAll(
    			() -> assertNotNull(e_saved2.getId()),
    			() -> assertNotNull(e_saved2.getNome()),
    			() -> assertNotNull(e_saved2.getCurso())
    		);
        
        Estudante e3 = dbTesting.inTransaction(() -> daoEstudante.get( e_saved2.getId() ));
        dbTesting.inTransaction(() -> daoEstudante.remove(e_saved2));
        
        assertNull( dbTesting.inTransaction(() -> daoEstudante.get( e3.getId() )) );  
        
        List<Estudante> estudantes = new ArrayList<Estudante>();
        estudantes.add(e_saved);
        
        assertTrue( estudantes.equals(  dbTesting.inTransaction(() -> daoEstudante.list()) ));
    }
    
    @Test
    public void testUpdate() {

        System.out.println("EstudanteDAO - test update");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
    	
        Estudante e = new Estudante("Ailton", c_saved);  
        Estudante e_saved = dbTesting.inTransaction(() -> daoEstudante.persist(e));

        e_saved.setId((long) 1);
        e_saved.setNome("Roberta");
        e_saved.setScore((long) 20);
        
        
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        disciplinas.add(d);
        e_saved.setDisciplinas(disciplinas);
        
        List<Long> pre_req = new ArrayList<Long>();
        pre_req.add((long) 1);
        e_saved.setPre_disciplinas(pre_req);
        
        Curso c2 = new Curso("Biologia", Tipo.GRADUACAO, dep);
        e_saved.setCurso(c2);
        
        
        assertAll(
        			() -> assertEquals(1, e_saved.getId()),
        			() -> assertEquals("Roberta", e_saved.getNome()),
        			() -> assertEquals(20, e_saved.getScore()),
        			() -> assertTrue( pre_req.equals( e_saved.getPre_disciplinas() )),
        			() -> assertTrue( disciplinas.equals( e_saved.getDisciplinas())),
        			() -> assertEquals("Biologia", e_saved.getCurso().getNome())
        		);
    }
    
    @Test
    public void testDelete() {

        System.out.println("EstudanteDAO - test delete");
        

        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
    	
    	Estudante e = new Estudante("Ailton", c_saved);  
        Estudante e_saved = dbTesting.inTransaction(() -> daoEstudante.persist(e));

        
        dbTesting.inTransaction(() -> daoEstudante.remove( e_saved ));

        assertNull( dbTesting.inTransaction(() -> daoEstudante.get( e_saved.getId() ))); 
    }
}
    