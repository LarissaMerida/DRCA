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

public class EstudanteTest {
	@Test
    public void testConstrutor() {
		Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, d);
    	
    	Estudante e = new Estudante("Ailton", c);
    	
    	assertAll(
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Estudante(null, c)),
	    			() -> assertThrows(IllegalArgumentException.class, () -> new Estudante("", c)),
	    			() -> assertThrows(NullPointerException.class, () -> new Estudante("Ana", null))
    			);
     
    	assertAll(
    				() -> assertNotNull( e.getId() ),
    				() -> assertEquals("Ailton", e.getNome() )
    			);
    }
    
    @Test
    public void testGetters() {
    	Universidade u = new Universidade("UFAL");
    	Departamento d = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, d);
    	
    	Curso curso = new Curso("Biologia", Tipo.GRADUACAO, d);
    	
    	Estudante e = new Estudante("Ailton", c);

    	e.setId( 1);
    	e.setNome("Alan");
    	e.setCurso(curso);
    	e.setScore( (long) 20);
    	
    	List<Disciplina> disciplinas = new ArrayList<Disciplina>();
    	Disciplina disciplina = new Disciplina("Árvore Filogénetica", DisciplinaTipo.ELETIVA, Tipo.POS_GRADUACAO);
    	disciplinas.add(disciplina);
    	
    	e.setDisciplinas(disciplinas);
    	
    	List<Long> pre_disciplinas = new ArrayList<Long>();
    	pre_disciplinas.add((long) 1);
    	e.setPre_disciplinas(pre_disciplinas);
   
        assertAll(
	    		   () -> assertEquals(1, e.getId() ),
	    		   () -> assertEquals("Alan", e.getNome()),
	    		   () -> assertEquals("Biologia", e.getCurso().getNome()),
	    		   () -> assertEquals(20, e.getScore() ),
	    		   () -> assertTrue( disciplinas.equals( e.getDisciplinas() )),
	    		   () -> assertTrue( pre_disciplinas.equals( e.getPre_disciplinas() ))
     		   );
    }
}
