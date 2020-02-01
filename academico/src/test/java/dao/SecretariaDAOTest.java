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
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SecretariaDAOTest {
	
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
    private CursoDAO cursoDAO;
    private SecretariaDAO daoSecretaria;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("SecretariaDAO - setUp ");
        daoUniversidade = new UniversidadeDAO(dbTesting.getSessionFactory());
        daoDepartamento = new DepartamentoDAO(dbTesting.getSessionFactory());
        daoSecretaria = new SecretariaDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("SecretariaDAO - test create");
        
        Universidade u = new Universidade("UFAL");        
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
        Departamento d = new Departamento("Instituto de Computação", u);
        Departamento d_saved = dbTesting.inTransaction(() -> daoDepartamento.persist(d));
        
        Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
        Secretaria s_saved = dbTesting.inTransaction(() -> daoSecretaria.persist(s));
        
        
        assertNotNull(s_saved);
        
        assertThrows(NullPointerException.class, () -> { new Secretaria(null, Tipo.GRADUACAO); });
        
        
        assertAll(
        			() -> assertNotNull(s_saved.getId()),
        			() -> assertNotNull(s_saved.getDepartamento()),
        			() -> assertNotNull(s_saved.getTipo())
        		);
        
        assertAll(
        			() -> assertEquals( s.getDepartamento()  , s_saved.getDepartamento()),
        			() -> assertNotNull(s_saved.getId())
        		);
        
        s_saved.setTipo( Tipo.POS_GRADUACAO ); 
        s_saved.setId( (long) 1);
        assertEquals(Tipo.POS_GRADUACAO, s_saved.getTipo());
        assertEquals(1, s_saved.getId());
    }
    
    @Test
    public void testRead() {

        System.out.println("SecretariaDAO - test read");
        
        Universidade u = new Universidade("UFAL");        
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
        Departamento d = new Departamento("Instituto de Computação", u);
        Departamento d_saved = dbTesting.inTransaction(() -> daoDepartamento.persist(d));
        
        Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
        Secretaria s_saved = dbTesting.inTransaction(() -> daoSecretaria.persist(s));
             
        assertAll(
        			() -> assertEquals(s.getId(), s_saved.getId()),
        			() -> assertEquals(s.getTipo(), s_saved.getTipo()),
        			() -> assertEquals(s.getDepartamento(), s_saved.getDepartamento()),
        			() -> assertTrue( s.getCursos().equals( s_saved.getCursos() ))
        		);
        
        
        Secretaria s2 = new Secretaria(d, Tipo.POS_GRADUACAO);
        Secretaria saved2 = dbTesting.inTransaction(() -> daoSecretaria.persist(s2));
        
        assertAll(
    			() -> assertNotNull(saved2.getId()),
    			() -> assertNotNull(saved2.getTipo()),
    			() -> assertNotNull(saved2.getDepartamento())
    		);
        
        Secretaria s3 = dbTesting.inTransaction(() -> daoSecretaria.get( saved2.getId() ));
        dbTesting.inTransaction(() -> daoSecretaria.remove(saved2));
        
        assertNull( dbTesting.inTransaction(() -> daoSecretaria.get( s3.getId() )) );
        
        List<Secretaria> secretarias = new ArrayList<Secretaria>();
        secretarias.add( s );
        
        assertTrue(secretarias.equals(  dbTesting.inTransaction(() -> daoSecretaria.list())  ));
        
        List<Curso> c = new ArrayList<Curso>();
    	Curso curso = new Curso("Ciência da computação", Tipo.POS_GRADUACAO, d);
    	c.add(curso);
    	
    	s_saved.setCursos( c );
    	
    	assertTrue( s_saved.getCursos().equals( c ));
    	assertTrue( s.getCursos().equals( s_saved.getCursos() ));
    }

    
    @Test
    public void testUpdate() {

        System.out.println("SecretariaDAO - test update");
        
        Universidade u = new Universidade("UFAL");        
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
        Departamento d = new Departamento("Instituto de Computação", u);
        Departamento d_saved = dbTesting.inTransaction(() -> daoDepartamento.persist(d));
        
        Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
        Secretaria s_saved = dbTesting.inTransaction(() -> daoSecretaria.persist(s));
  
        s_saved.setTipo( Tipo.POS_GRADUACAO ); 
        
        assertAll(
        			() -> assertEquals(s.getTipo(), s_saved.getTipo()),
        			() -> assertEquals("Instituto de Computação", s.getDepartamento().getNome())
        		);

        Departamento d2 = new Departamento("Instituto de Biologia", u);
        Departamento d_saved2 = dbTesting.inTransaction(() -> daoDepartamento.persist(d2));
        
        Secretaria s2 = new Secretaria(d2, Tipo.GRADUACAO);
        Secretaria s_saved2 = dbTesting.inTransaction(() -> daoSecretaria.persist(s2));

        s2.setDepartamento( d2 );
        s2.setTipo( Tipo.POS_GRADUACAO );
        s2.setId((long) 1);
        s2.setDepartamento( d );
        
        assertEquals(s2.getTipo(), s_saved2.getTipo()); 
        assertEquals(s2.getDepartamento().getNome(), s_saved2.getDepartamento().getNome());
        assertEquals(s2.getId(), s_saved2.getId());
        assertTrue( s2.getCursos().equals( s_saved2.getCursos() )); 
        assertEquals( 1, s2.getId() );
        assertEquals("Instituto de Computação", s2.getDepartamento().getNome() );
    }
    
    @Test
    public void testDelete() {

        System.out.println("SecretariaDAO - test delete");
        
        Universidade u = new Universidade("UFAL");        
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
        Departamento d = new Departamento("Instituto de Computação", u);
        Departamento d_saved = dbTesting.inTransaction(() -> daoDepartamento.persist(d));
        
        Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
        Secretaria s_saved = dbTesting.inTransaction(() -> daoSecretaria.persist(s));

        dbTesting.inTransaction(() -> daoSecretaria.remove( s_saved ));

        assertNull( dbTesting.inTransaction(() -> daoSecretaria.get( s_saved.getId() ))); 
    }
}
    