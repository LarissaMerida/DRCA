package br.ufal.ic.academico.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufal.ic.academico.model.Secretaria.Tipo;


public class SecretariaTest {

    @Test
    public void testConstrutor() {
    	Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
    	
        assertThrows(NullPointerException.class, () -> new Secretaria(null, Tipo.GRADUACAO));
     
        assertNull(s.getId() );
        assertNotNull( s.getDepartamento() );
        assertNotNull(s.getTipo());
        
        List<Curso> c = new ArrayList<Curso>();
        assertTrue( c.equals( s.getCursos() ) );
    }
    
    @Test
    public void testGetters() {
    	Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Secretaria s = new Secretaria(d, Tipo.GRADUACAO);
    	
    	Universidade universidade = new Universidade("Cesmac");
    	Universidade u2 = new Universidade("UFAL");
    	Departamento departamento = new Departamento("Instituto de Computação2", u2);
        
    	s.setDepartamento(departamento);
    	s.setId((long) 1);
    	s.setTipo( Tipo.POS_GRADUACAO );
    	
    	List<Curso> c = new ArrayList<Curso>();
    	Curso curso = new Curso("Ciência da computação", Tipo.POS_GRADUACAO, d);
    	c.add(curso);
    	
    	s.setCursos( c );
   
        assertAll(
	    		   () -> assertTrue( c.equals( s.getCursos() )),
	    		   () -> assertEquals("Instituto de Computação2", s.getDepartamento().getNome()),
	    		   () -> assertEquals(1, s.getId()),
	    		   () -> assertEquals( Tipo.POS_GRADUACAO, s.getTipo() )
     		   );
    }
}
