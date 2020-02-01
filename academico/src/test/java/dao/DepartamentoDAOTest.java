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

import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Universidade;
import dao.DepartamentoDAO;
import dao.UniversidadeDAO;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class DepartamentoDAOTest {
	
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
    		.addEntityClass(Universidade.class)
            .addEntityClass(Departamento.class).build();
    
    
    private DepartamentoDAO dao;
    private UniversidadeDAO daoUniversidade;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("DepartamentoDAO - setUp ");
        daoUniversidade = new UniversidadeDAO(dbTesting.getSessionFactory());
        dao = new DepartamentoDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("DepartamentoDAO - test create");
        
        Universidade u = new Universidade("UFAL");        
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));
        
        Departamento d = new Departamento("Instituto de Computação", u);

        Departamento saved = dbTesting.inTransaction(() -> dao.persist(d));
        
        assertNotNull(saved);
        
        assertAll(
	        		() -> assertThrows(IllegalArgumentException.class, () -> { new Departamento("", u); }),
	        		() -> assertThrows(IllegalArgumentException.class, () -> { new Departamento(null, u); })
        		);
        
        
        assertAll(
        			() -> assertNotNull(saved.getId()),
        			() -> assertNotNull(saved.getNome())
        		);
        
        assertAll(
        			() -> assertEquals(d.getNome(), saved.getNome()),
        			() -> assertNotNull(d.getId())
        		);
        
        saved.setNome("Insttituto de Biologia");
        saved.setId( (long) 1);
        assertEquals("Insttituto de Biologia", saved.getNome());
        assertEquals(1, saved.getId());
    }
    
    @Test
    public void testRead() {

        System.out.println("DepartamentoDAO - test read");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));

        Departamento d = new Departamento("Instituto de Computação", u);
        
        Departamento saved = dbTesting.inTransaction(() -> dao.persist(d));
        Departamento d1 = dbTesting.inTransaction(() -> dao.get( saved.getId() ));
             
        assertAll(
        			() -> assertEquals(d1.getId(), saved.getId()),
        			() -> assertEquals(d1.getNome(), saved.getNome()),
        			() -> assertEquals(d1.getUniversidade(), saved.getUniversidade())
        		);
        
        
        Departamento d2 = new Departamento("Instituto de Computação2", u);
        Departamento saved2 = dbTesting.inTransaction(() -> dao.persist(d2));
        
        assertAll(
    			() -> assertNotNull(saved2.getId()),
    			() -> assertNotNull(saved2.getNome()),
    			() -> assertNotNull(saved2.getUniversidade())
    		);
        
        dbTesting.inTransaction(() -> dao.remove(saved2));
        Departamento d3 = dbTesting.inTransaction(() -> dao.get( saved2.getId() ));
        
        assertNull( d3 );
        
        List<Departamento> depart = new ArrayList<Departamento>();
        depart.add( d );
        
        assertTrue(depart.equals(  dbTesting.inTransaction(() -> dao.list())  ));
    }
    
    @Test
    public void testUpdate() {

        System.out.println("DepartamentoDAO - test update");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));

        Departamento d = new Departamento("Instituto de Computação", u);
        
        Departamento saved = dbTesting.inTransaction(() -> dao.persist(d));
        Departamento d3 = dbTesting.inTransaction(() -> dao.get( saved.getId() ));
        
        saved.setNome("Instituto de Computação2");
        
        
        assertAll(
        			() -> assertEquals(d.getNome(), saved.getNome()),
        			() -> assertEquals(d3.getNome(), saved.getNome()),
        			() -> assertEquals("Instituto de Computação2", saved.getNome()),
        			() -> assertEquals("Instituto de Computação2", d3.getNome()),
        			() -> assertEquals("UFAL", d3.getUniversidade().getNome())
        		);

        Universidade u2 = new Universidade("CESMAC");
        Universidade u_saved2 = dbTesting.inTransaction(() -> daoUniversidade.persist(u2));
        d3.setUniversidade( u2 );
        d.setNome("Instituto de Computação3");
        
        assertEquals(d.getNome(), saved.getNome()); 
        assertEquals(d3.getNome(), saved.getNome());
        assertEquals(d.getUniversidade().getNome(), saved.getUniversidade().getNome());
        assertEquals(d3.getUniversidade().getNome(), saved.getUniversidade().getNome());
        
    }
    
    @Test
    public void testDelete() {

        System.out.println("DepartamentoDAO - test delete");
        
        Universidade u = new Universidade("UFAL");
        Universidade u_saved = dbTesting.inTransaction(() -> daoUniversidade.persist(u));

        Departamento d = new Departamento("Instituto de Computação", u);
        
        Departamento saved = dbTesting.inTransaction(() -> dao.persist(d));
        dbTesting.inTransaction(() -> dao.remove( saved ));

        assertNull( dbTesting.inTransaction(() -> dao.get( saved.getId() ))); 
    }
}
