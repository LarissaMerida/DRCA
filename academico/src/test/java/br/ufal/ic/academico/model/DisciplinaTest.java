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

public class DisciplinaTest {
	@Test
    public void testConstrutor() {
    	Professor p = new Professor("Ailton");
    	Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);
    	
    	assertAll(
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Disciplina(null, DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO)),
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Disciplina("", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO))
    			);
     
    	assertAll(
    				() -> assertNotNull( d.getCreditos()),
    				() -> assertNotNull( d.getEstudantes() ),
    				() -> assertNull( d.getId() ),
    				() -> assertNotNull( d.getMin_creditos() ),
    				() -> assertNotNull( d.getNivel() ),
    				() -> assertNotNull( d.getTipo()),
    				() -> assertNull( d.getProfessor() )
    			);
    	
    	List<Estudante> e = new ArrayList<Estudante>();
    	d.setProfessor(p);
    	
    	assertAll(
    				() -> assertEquals("Programação 1", d.getNome() ),
    				() -> assertEquals("Ailton", d.getProfessor().getNome()),
    				() -> assertEquals(Tipo.GRADUACAO, d.getNivel()),
    				() -> assertEquals(DisciplinaTipo.OBRIGATORIA, d.getTipo()),
    				() -> assertEquals(0, d.getCreditos() ),
    				() -> assertEquals(0, d.getMin_creditos()),
    				() -> assertTrue( e.equals( d.getEstudantes() ))
    			);
    }
    
    @Test
    public void testGetters() {
    	Professor p = new Professor("Ailton");
    	Disciplina d = new Disciplina("Programacação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);
    	
    	d.setCreditos( (long) 20 );
    	d.setId((long) 1);
    	d.setMin_creditos( (long) 30 );
    	d.setNivel( Tipo.POS_GRADUACAO );
    	d.setTipo(DisciplinaTipo.ELETIVA );
    	d.setProfessor( p );
    	d.setNome( "P1" );
    
    	List<Estudante> e = new ArrayList<Estudante>();
    	
    	Universidade u = new Universidade("UFAL");
    	Departamento departamento = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da computação", Tipo.POS_GRADUACAO, departamento);
    	Estudante estudante = new Estudante( "Larissa", c);
    	e.add(estudante);
    	
    	d.setEstudantes(e);
    	
    	List<Long> pre_disciplinas = new ArrayList<Long>();
    	pre_disciplinas.add((long) 1);
    	d.setPre_disciplinas(pre_disciplinas);
   
        assertAll(
	    		   () -> assertTrue( e.equals( d.getEstudantes() )),
	    		   () -> assertEquals("P1", d.getNome()),
	    		   () -> assertEquals(20, d.getCreditos() ),
	    		   () -> assertEquals(1, d.getId() ),
	    		   () -> assertEquals(Tipo.POS_GRADUACAO, d.getNivel() ),
	    		   () -> assertEquals(DisciplinaTipo.ELETIVA, d.getTipo()),
	    		   () -> assertEquals("Ailton", d.getProfessor().getNome()),
	    		   () -> assertTrue( pre_disciplinas.equals( d.getPre_disciplinas() ))
     		   );
    }
}
