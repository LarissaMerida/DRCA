package integracao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.ConfigApp;
import br.ufal.ic.academico.model.Universidade;
import ch.qos.logback.classic.Level;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UniversidadeTest {
	static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }

    public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension(AcademicoApp.class,
            ResourceHelpers.resourceFilePath("config-test.yml"));
    
    
    @Test
    public void testGetList() {
    	Universidade p = new Universidade("UFAL");
        
        Universidade saved = RULE.client().target(
             String.format("http://localhost:%d/%s/universidades", RULE.getLocalPort(), "academicotest"))
            .request()
            .post(Entity.json(p), Universidade.class);

        assertNotNull(saved.getId());
        
        List<Universidade> list = RULE.client().target(
             String.format("http://localhost:%d/%s/universidades", RULE.getLocalPort(), "academicotest"))
            .request()
            .get(new GenericType<List<Universidade>>() {});

        assertEquals(2, list.size());
    }
    
    @Test
    public void testGet() {
    	Universidade p = new Universidade("UFAL");
        
    	System.out.println("testGet: "+ p.getId());
    	
        Universidade saved = RULE.client().target(
             String.format("http://localhost:%d/%s/universidades", RULE.getLocalPort(), "academicotest"))
            .request()
            .post(Entity.json(p), Universidade.class);

        assertNotNull(saved.getId());
        
        Universidade universidade = RULE.client().target(
             String.format("http://localhost:%d/%s/universidades/%d", RULE.getLocalPort(), "academicotest", saved.getId()))
            .request()
            .get(new GenericType<Universidade>() {});

        assertAll(
        			() -> assertEquals("UFAL", universidade.getNome() ),
        			() -> assertNull( universidade.getTelefone() ),
        			() -> assertNotNull( universidade.getId() )
        		
        		);
    }
    
}
