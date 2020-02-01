package br.ufal.ic.academico.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import br.ufal.ic.academico.model.Secretaria.Tipo;

public class ProfessorTest {
	@Test
    public void testConstrutor() {
    	Professor p = new Professor("Ailton");
    	
    	assertAll(
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Professor(null)),
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Professor(""))
    			);
     
    	assertAll(
    				() -> assertNull( p.getId() ),
    				() -> assertEquals("Ailton", p.getNome() )
    			);
    }
    
    @Test
    public void testGetters() {
    	Professor p = new Professor("Ailton");

    	p.setId( (long) 1);
    	p.setNome("Alan");
   
        assertAll(
	    		   () -> assertEquals(1, p.getId() ),
	    		   () -> assertEquals("Alan", p.getNome())
     		   );
    }
}
