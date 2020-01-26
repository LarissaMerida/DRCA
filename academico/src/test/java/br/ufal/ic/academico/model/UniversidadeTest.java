package br.ufal.ic.academico.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class UniversidadeTest {

    @Test
    public void testConstrutor() {
        assertThrows(IllegalArgumentException.class, () -> new Universidade(null));
        assertThrows(IllegalArgumentException.class, () -> new Universidade(""));
        
        Universidade u = new Universidade("UFAL");
     
        assertNull(u.getId() );
        assertNull( u.getTelefone() );
        assertNotNull(u.getNome());  
    }
    
    @Test
    public void testGetters() {
    	Universidade universidade = new Universidade("Cesmac");
        
    	universidade.setTelefone("3312-1212");
    	universidade.setId((long) 1);
    	universidade.setNome( "UFAL");
   
        assertAll(
	    		   () -> assertEquals("UFAL", universidade.getNome()),
	    		   () -> assertEquals("3312-1212", universidade.getTelefone()),
	    		   () -> assertEquals(1, universidade.getId())
    		   );
    }
}
