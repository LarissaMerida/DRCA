package br.ufal.ic.academico.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class DepartamentoTest {

    @Test
    public void testConstrutor() {
    	Universidade u = new Universidade("UFAL");
        assertThrows(IllegalArgumentException.class, () -> new Departamento(null, u));
        assertThrows(IllegalArgumentException.class, () -> new Departamento("", u));
        assertThrows(NullPointerException.class, () -> new Departamento("SSS", null));
        
        Departamento d = new Departamento("Instituto de Computação", u);
     
        assertNull(d.getId() );
        assertNotNull( d.getUniversidade() );
        assertNotNull(d.getNome());  
    }
    
    @Test
    public void testGetters() {
    	Universidade universidade = new Universidade("Cesmac");
    	Universidade u2 = new Universidade("UFAL");
    	Departamento departamento = new Departamento("Instituto de Computação", u2);
        
    	departamento.setUniversidade( u2 );
    	departamento.setId((long) 1);
    	departamento.setNome("UFAL");
   
        assertAll(
	    		   () -> assertEquals("UFAL", departamento.getNome()),
	    		   () -> assertEquals("UFAL", departamento.getUniversidade().getNome()),
	    		   () -> assertEquals(1, departamento.getId())
    		   );
        
        departamento.setNome("Instituto de Computação2");
        assertEquals("Instituto de Computação2", departamento.getNome());
    }
}
