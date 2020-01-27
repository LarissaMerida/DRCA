package DAO;


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

import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class DisciplinaDAOTest {
	
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Curso.class)
            .addEntityClass(Disciplina.class)
            .addEntityClass(Departamento.class)
            .addEntityClass(Universidade.class)
            .addEntityClass(Estudante.class)
            .addEntityClass(Professor.class).build();
    
    
    private DepartamentoDAO daoDepartamento;
    private UniversidadeDAO daoUniversidade;
    private CursoDAO cursoDAO;
    private SecretariaDAO daoSecretaria;
    private DisciplinaDAO daoDisciplina;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("DisciplinaDAO - setUp ");
        daoDepartamento = new DepartamentoDAO(dbTesting.getSessionFactory());
        daoDisciplina = new DisciplinaDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("DisciplinaDAO - test create");
        
        Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        Disciplina d_saved = dbTesting.inTransaction(() -> daoDisciplina.persist(d));

        assertNotNull(d_saved);
        
        assertAll(
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Disciplina(null, DisciplinaTipo.OBRIGATORIA,Tipo.GRADUACAO); }),
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Disciplina("",DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO); })
        		);
   
        List<Estudante> e = new ArrayList<Estudante>();
        List<Long> pre_req = new ArrayList<Long>();
 
        assertAll(
        			() -> assertNotNull(d_saved.getId()),
        			() -> assertNotNull(d_saved.getNivel()),
        			() -> assertNotNull(d_saved.getTipo()),
        			() -> assertNull(d_saved.getProfessor()),
        			() -> assertEquals(0, d_saved.getCreditos()),
        			() -> assertEquals(0, d_saved.getMin_creditos()),
        			() -> assertEquals("Programação 1", d_saved.getNome()),
        			() -> assertTrue( e.equals( d_saved.getEstudantes()) ),
        			() -> assertTrue( pre_req.equals( d_saved.getPre_disciplinas() ) )
        		);
        
        
        assertAll(
        			() -> assertEquals( d.getTipo() , d_saved.getTipo()),
        			() -> assertEquals(d.getId(), d_saved.getId()),
        			() -> assertEquals(d.getTipo(), d_saved.getTipo()),
        			() -> assertEquals(d.getNivel(), d_saved.getNivel()),
        			() -> assertNotNull(d_saved.getId())
        		);
        
        
        d_saved.setNivel(Tipo.POS_GRADUACAO);
        d_saved.setId( (long) 1);
        d_saved.setCreditos((long) 10);
        d_saved.setMin_creditos((long) 13);
        d_saved.setNome("Árvore filogenética");
        
        Professor professor = new Professor("Ailton");
        d_saved.setProfessor(professor);
        
        assertAll(
        			() -> assertEquals(Tipo.POS_GRADUACAO, d_saved.getNivel()),
        			() -> assertEquals(DisciplinaTipo.OBRIGATORIA, d_saved.getTipo()),
        			() -> assertEquals(1, d_saved.getId()),
        			() -> assertEquals(10, d_saved.getCreditos()),
        			() -> assertEquals(13, d_saved.getMin_creditos()),
        			() -> assertEquals("Árvore filogenética", d_saved.getNome()),
        			() -> assertEquals("Ailton", d_saved.getProfessor().getNome())
        		);
    }
    
    @Test
    public void testRead() {

        System.out.println("DisciplinaDAO - test read");
        
        Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        Disciplina d_saved = dbTesting.inTransaction(() -> daoDisciplina.persist(d));
    
        assertAll(
        			//() -> assertEquals(d.getId(), d_saved.getId()),
        			() -> assertEquals(d.getTipo(), d_saved.getTipo()),
        			() -> assertEquals(d.getNome(), d_saved.getNome()),
        			() -> assertTrue( d.getEstudantes().equals( d_saved.getEstudantes() )),
        			() -> assertEquals( d.getCreditos(), d_saved.getCreditos()),
        			() -> assertEquals( d.getMin_creditos(), d_saved.getMin_creditos()),
        			() -> assertEquals( d.getNivel(), d_saved.getNivel())
        			//() -> assertTrue( d.getPre_disciplinas().equals( d_saved.getPre_disciplinas()))
        			//() -> assertEquals( d.getProfessor().getNome(), d_saved.getProfessor().getNome())
        		);
        
        
        Disciplina d2 = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        Disciplina d_saved2 = dbTesting.inTransaction(() -> daoDisciplina.persist(d2));
    
        assertAll(
    			() -> assertNotNull(d_saved2.getId()),
    			() -> assertNotNull(d_saved2.getTipo()),
    			() -> assertNotNull(d_saved2.getNivel()),
    			() -> assertNull(d_saved2.getProfessor())
    		);
        
        Disciplina d3 = dbTesting.inTransaction(() -> daoDisciplina.get( d_saved2.getId() ));
        dbTesting.inTransaction(() -> daoDisciplina.remove(d_saved2));
        
        assertNull( dbTesting.inTransaction(() -> daoDisciplina.get( d3.getId() )) );
       
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        disciplinas.add(d);
        
        assertTrue(disciplinas.equals(  dbTesting.inTransaction(() -> daoDisciplina.list())  ));
        
        List<Estudante> estudantes = new ArrayList<Estudante>();
        Universidade u = new Universidade("UFAL");
    	Departamento dep = new Departamento("Instituto de Computação", u);
    	Curso c = new Curso("Ciência da Computação", Tipo.GRADUACAO, dep);
    	
        Estudante e = new Estudante("Ana", c);
        estudantes.add( e );
      
    	
    	d_saved.setEstudantes(estudantes);
    	
    	assertTrue( estudantes.equals( d_saved.getEstudantes()));
    	
    	List<Long> pre_req = new ArrayList<Long>();
    	pre_req.add((long) 1);
    	d_saved.setPre_disciplinas(pre_req);
    	assertTrue( pre_req.equals( d_saved.getPre_disciplinas() ));
    }

    
    @Test
    public void testUpdate() {

        System.out.println("DisciplinaDAO - test update");
        
        Disciplina d = new Disciplina("Programação 1", DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);  
        Disciplina d_saved = dbTesting.inTransaction(() -> daoDisciplina.persist(d));
    
        d_saved.setNivel( Tipo.POS_GRADUACAO ); 
        
        assertAll(
        			() -> assertEquals(d.getTipo(), d_saved.getTipo()),
        			() -> assertEquals(d.getNivel(), d_saved.getNivel()),
        			() -> assertEquals("Programação 1", d_saved.getNome())
        		);
        
        Professor professor = new Professor("Ailton");
       
        d_saved.setProfessor(professor);;
        d_saved.setNivel( Tipo.POS_GRADUACAO );
        d_saved.setId((long) 1);
        d_saved.setTipo( DisciplinaTipo.ELETIVA);
        
        assertEquals(d.getTipo(), d_saved.getTipo()); 
        assertEquals(d.getProfessor().getNome(), d_saved.getProfessor().getNome());
        assertEquals(d.getId(), d_saved.getId());
        assertTrue( d.getEstudantes().equals( d_saved.getEstudantes() )); 
        assertEquals( 1, d.getId() );
        assertEquals("Programação 1", d_saved.getNome() );
    }
    
    @Test
    public void testDelete() {

        System.out.println("DisciplinaDAO - test delete");
        
        Disciplina d = new Disciplina("Biologia",DisciplinaTipo.OBRIGATORIA, Tipo.GRADUACAO);        
        Disciplina d_saved = dbTesting.inTransaction(() -> daoDisciplina.persist(d));
        
       
        dbTesting.inTransaction(() -> daoDisciplina.remove( d_saved ));

        assertNull( dbTesting.inTransaction(() -> daoDisciplina.get( d_saved.getId() ))); 
    }
}
    