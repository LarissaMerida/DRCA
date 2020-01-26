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

public class CursoTest {
	@Test
    public void testConstrutor() {
    	Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, d);
    	
    	assertAll(
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Curso(null, Tipo.GRADUACAO, d)),
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Curso("", Tipo.GRADUACAO, d)),
	    			() -> assertThrows(NullPointerException.class, () -> new Curso("AAA", Tipo.GRADUACAO, null))
    			);
     
    	List<Disciplina>  disciplinas = new ArrayList<Disciplina>();
    	assertAll(
    				() -> assertEquals("Ciência da Computação", c.getNome()),
    				() -> assertNotNull( c.getDepartamento() ),
    				() -> assertEquals("Instituto de Computação", c.getDepartamento().getNome()),
    				() -> assertNull( c.getId() ),
    				() -> assertEquals(Tipo.GRADUACAO, c.getTipo()),
    				() -> assertTrue( disciplinas.equals( c.getDisciplinas() ))
    			
    			);
    }
    
    @Test
    public void testGetters() {
    	Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, d);
    	
    	Departamento d2 = new Departamento("Instituto de Biologia", u);
    	
    	c.setId((long) 1);
    	c.setNome( "Analise de Sistemas");
    	c.setTipo(Tipo.POS_GRADUACAO);
    	c.setDepartamento( d2 );
    	
    	
    	List<Disciplina> disciplinas = new ArrayList<Disciplina>();
    	Disciplina disciplina = new Disciplina("Árvore Filogénetica", DisciplinaTipo.ELETIVA, Tipo.POS_GRADUACAO);
    	disciplinas.add(disciplina);
    	
    	c.setDisciplinas(disciplinas);
   
        assertAll(
	    		   () -> assertTrue( disciplinas.equals( c.getDisciplinas() )),
	    		   () -> assertEquals("Analise de Sistemas", c.getNome()),
	    		   () -> assertEquals(1, c.getId()),
	    		   () -> assertEquals( Tipo.POS_GRADUACAO, c.getTipo() ),
	    		   () -> assertEquals( "Instituto de Biologia", c.getDepartamento().getNome())
     		   );
    }
}
