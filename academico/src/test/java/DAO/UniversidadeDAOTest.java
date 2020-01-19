package DAO;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.ufal.ic.academico.exemplos.Person;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UniversidadeDAOTest {
    
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Universidade.class).build();
    
    private UniversidadeDAO dao;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("UniversidadeDAO - setUp ");
        dao = new UniversidadeDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("UniversidadeDAO - test create");
        
        Universidade u = new Universidade("UFAL");
        
        Universidade saved = dbTesting.inTransaction(() -> dao.persist(u));
        
        assertNotNull(saved);
        
        assertAll(
        			() -> assertNotNull(saved.getId()),
        			() -> assertNotNull(saved.getNome()),
        			() -> assertNull(saved.getTelefone())
        		);
        
        assertAll(
        			() -> assertEquals(u.getNome(), saved.getNome()),
        			() -> assertEquals(u.getTelefone(), saved.getTelefone()),
        			() -> assertNotNull(u.getId())
        		);
    }
    
    @Test
    public void testRead() {

        System.out.println("UniversidadeDAO - test read");
        
        Universidade u = new Universidade("UFAL");
        u.setTelefone("3312-1212");
        Universidade saved = dbTesting.inTransaction(() -> dao.persist(u));
        Universidade u1 = dbTesting.inTransaction(() -> dao.get( saved.getId() ));
        
        assertAll(
        			() -> assertEquals(u1.getId(), saved.getId()),
        			() -> assertEquals(u1.getNome(), saved.getNome()),
        			() -> assertEquals(u1.getTelefone(), saved.getTelefone())	
        		);
        
        
        Universidade u2 = new Universidade("CESMAC");
        Universidade saved2 = dbTesting.inTransaction(() -> dao.persist(u2));
        
        assertAll(
    			() -> assertNotNull(saved2.getId()),
    			() -> assertNotNull(saved2.getNome()),
    			() -> assertNull(saved2.getTelefone())	
    		);
        
        dbTesting.inTransaction(() -> dao.remove(u2));
        Universidade u3 = dbTesting.inTransaction(() -> dao.get( u2.getId() ));
        
        assertNull( u3 );
        
    }
    
    @Test
    public void testUpdate() {

        System.out.println("UniversidadeDAO - test update");
        
        Universidade u = new Universidade("UFAL");
        u.setTelefone("3312-1212");
        
        Universidade saved = dbTesting.inTransaction(() -> dao.persist(u));
        Universidade u3 = dbTesting.inTransaction(() -> dao.get( saved.getId() ));
        
        saved.setTelefone("3313-1313");
        saved.setNome("CESMAC");
        
        
        assertAll(
        			() -> assertEquals(u.getTelefone(), saved.getTelefone()),
        			() -> assertEquals(u3.getTelefone(), saved.getTelefone()),
        			() -> assertEquals("3313-1313", saved.getTelefone()),
        			() -> assertEquals("3313-1313", u3.getTelefone()),
        			() -> assertEquals(u.getNome(), saved.getNome()),
        			() -> assertEquals(u3.getNome(), saved.getNome()),
        			() -> assertEquals("CESMAC", saved.getNome()),
        			() -> assertEquals("CESMAC", u3.getNome())
        		);

        u.setNome("UFAL");
        assertEquals(u.getNome(), saved.getNome()); 
        assertEquals(u3.getNome(), saved.getNome()); 
    }
    
    @Test
    public void testDelete() {

        System.out.println("UniversidadeDAO - test delete");
        
        Universidade u = new Universidade("UFAL");
        u.setTelefone("3312-1212");
        
        Universidade saved = dbTesting.inTransaction(() -> dao.persist(u));
        dbTesting.inTransaction(() -> dao.remove( saved ));

        assertNull( dbTesting.inTransaction(() -> dao.get( saved.getId() ))); 
    }
}
