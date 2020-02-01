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
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import dao.CursoDAO;
import dao.DepartamentoDAO;
import dao.SecretariaDAO;
import dao.UniversidadeDAO;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CursoDAOTest {
	
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
    		.addEntityClass(Universidade.class)
            .addEntityClass(Departamento.class)
            .addEntityClass(Curso.class)
            .addEntityClass(Disciplina.class)
            .addEntityClass(Professor.class)
            .addEntityClass(Estudante.class)
            .addEntityClass(Secretaria.class).build();
    
    
    private DepartamentoDAO daoDepartamento;
    private UniversidadeDAO daoUniversidade;
    private CursoDAO daoCurso;
    private SecretariaDAO daoSecretaria;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("CursoDAO - setUp ");
        daoUniversidade = new UniversidadeDAO(dbTesting.getSessionFactory());
        daoDepartamento = new DepartamentoDAO(dbTesting.getSessionFactory());
        daoCurso = new CursoDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("CursoDAO - test create");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
        
        
        assertNotNull(c_saved);
        
        assertAll(
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Curso(null, Tipo.GRADUACAO, dep); }),
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Curso("", Tipo.GRADUACAO, dep); }),
        			() -> assertThrows(NullPointerException.class, () -> { new Curso("AA", Tipo.GRADUACAO, null); })
        		);
        
        
        assertAll(
        			() -> assertNotNull(c_saved.getId()),
        			() -> assertNotNull(c_saved.getDepartamento()),
        			() -> assertNotNull(c_saved.getTipo())
        		);
        
        assertAll(
        			() -> assertEquals( c.getDepartamento()  , c_saved.getDepartamento()),
        			() -> assertNotNull(c_saved.getId())
        		);
        
        c_saved.setTipo( Tipo.POS_GRADUACAO ); 
        c_saved.setId( (long) 1);
        assertEquals(Tipo.POS_GRADUACAO, c_saved.getTipo());
        assertEquals(1, c_saved.getId());
    }
    
    @Test
    public void testRead() {

        System.out.println("CursoDAO - test read");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
             
        assertAll(
        			() -> assertEquals(c.getId(), c_saved.getId()),
        			() -> assertEquals(c.getTipo(), c_saved.getTipo()),
        			() -> assertEquals(c.getDepartamento(), c_saved.getDepartamento()),
        			() -> assertTrue( c.getDisciplinas().equals( c_saved.getDisciplinas() )),
        			() -> assertEquals("Ciência da Computação", c_saved.getNome() )
        		);
        
        
        Curso c2 = new Curso("História", Tipo.GRADUACAO, dep);
    	Curso c_saved2 = dbTesting.inTransaction(() -> daoCurso.persist(c2));
        
        assertAll(
    			() -> assertNotNull(c_saved2.getId()),
    			() -> assertNotNull(c_saved2.getTipo()),
    			() -> assertNotNull(c_saved2.getDepartamento())
    		);
        
        
        Curso c3 = dbTesting.inTransaction(() -> daoCurso.get( c_saved2.getId() ));
        dbTesting.inTransaction(() -> daoCurso.remove(c_saved2));
        
        assertNull( dbTesting.inTransaction(() -> daoCurso.get( c3.getId() )) );
        
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        disciplinas.add( d );
        
        c_saved.setDisciplinas(disciplinas);
        assertTrue( disciplinas.equals( c_saved.getDisciplinas() ));
        
        List<Curso> cursos = new ArrayList<Curso>();
        cursos.add(c);
        assertTrue( cursos.equals(  dbTesting.inTransaction(() -> daoCurso.list())  ));
      
    	assertTrue( c.getDisciplinas().equals( c_saved.getDisciplinas() ));
    }

    
    @Test
    public void testUpdate() {

        System.out.println("CursoDAO - test update");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));
  
    	c_saved.setTipo( Tipo.POS_GRADUACAO ); 
        
        assertAll(
        			() -> assertEquals(c.getTipo(), c_saved.getTipo()),
        			() -> assertEquals("Instituto de Computação", c.getDepartamento().getNome())
        		);

        Curso c2 = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved2 = dbTesting.inTransaction(() -> daoCurso.persist(c2));

        c2.setDepartamento( dep );
        c2.setTipo( Tipo.POS_GRADUACAO );
        c2.setId((long) 1);
        
        assertEquals(c2.getTipo(), c_saved2.getTipo()); 
        assertEquals(c2.getDepartamento().getNome(), c_saved2.getDepartamento().getNome());
        assertEquals(c2.getId(), c_saved2.getId());
        assertTrue( c2.getDisciplinas().equals( c_saved2.getDisciplinas() )); 
        assertEquals( 1, c2.getId() );
        assertEquals("Instituto de Computação", c2.getDepartamento().getNome() );
    }
    
    @Test
    public void testDelete() {

        System.out.println("CursoDAO - test delete");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Departamento d_saved =  dbTesting.inTransaction(() -> daoDepartamento.persist(dep));
    	
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	Curso c_saved = dbTesting.inTransaction(() -> daoCurso.persist(c));

        dbTesting.inTransaction(() -> daoCurso.remove( c_saved ));

        assertNull( dbTesting.inTransaction(() -> daoCurso.get( c_saved.getId() ))); 
    }
}
    