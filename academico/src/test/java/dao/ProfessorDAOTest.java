package dao;


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
import dao.CursoDAO;
import dao.DepartamentoDAO;
import dao.DisciplinaDAO;
import dao.ProfessorDAO;
import dao.SecretariaDAO;
import dao.UniversidadeDAO;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ProfessorDAOTest {
	
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
    private ProfessorDAO daoProfessor;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("ProfessorDAO - setUp ");
        daoDepartamento = new DepartamentoDAO(dbTesting.getSessionFactory());
        daoDisciplina = new DisciplinaDAO(dbTesting.getSessionFactory());
        daoProfessor = new ProfessorDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCreate() {

        System.out.println("ProfessorDAO - test create");
        
        Professor p = new Professor("Ailton");  
        Professor p_saved = dbTesting.inTransaction(() -> daoProfessor.persist(p));

        assertNotNull(p_saved);
        
        assertAll(
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Professor(null); }),
        			() -> assertThrows(IllegalArgumentException.class, () -> { new Professor(""); })
        		);
 
        assertAll(
        			() -> assertNotNull(p_saved.getId()),
        			() -> assertEquals("Ailton", p_saved.getNome())
        		);
        
        
        assertAll(
        			() -> assertEquals(p.getId(), p_saved.getId()),
        			() -> assertEquals(p.getNome(), p_saved.getNome())
        		);
        
        
        p_saved.setId( (long) 1);
        p_saved.setNome("Alan");
        
        assertAll(
        			() -> assertEquals(1, p_saved.getId()),
        			() -> assertEquals("Alan", p_saved.getNome())
        		);
    }
    
    @Test
    public void testRead() {

        System.out.println("ProfessorDAO - test read");
        
        Professor p = new Professor("Ailton");  
        Professor p_saved = dbTesting.inTransaction(() -> daoProfessor.persist(p));
    
        assertAll(
        			() -> assertEquals(p.getNome(), p_saved.getNome()),
        			() -> assertEquals( p.getId(), p_saved.getId())
        		);
        
        
        Professor p2 = new Professor("Ailton");  
        Professor p_saved2 = dbTesting.inTransaction(() -> daoProfessor.persist(p2));
    
        assertAll(
    			() -> assertNotNull(p_saved2.getId()),
    			() -> assertNotNull(p_saved2.getNome())
    		);
        
        Professor p3 = dbTesting.inTransaction(() -> daoProfessor.get( p_saved2.getId() ));
        dbTesting.inTransaction(() -> daoProfessor.remove(p_saved2));
        
        assertNull( dbTesting.inTransaction(() -> daoProfessor.get( p3.getId() )) );  
        
        List<Professor> professores = new ArrayList<Professor>();
        professores.add(p);
        
        assertTrue( professores.equals(  dbTesting.inTransaction(() -> daoProfessor.list()) ));
    }
    
    @Test
    public void testUpdate() {

        System.out.println("ProfessorDAO - test update");
        
        Professor p = new Professor("Ailton");  
        Professor p_saved = dbTesting.inTransaction(() -> daoProfessor.persist(p));
    
        p_saved.setId((long) 1);
        p_saved.setNome("Roberta");
        
        assertAll(
        			() -> assertEquals(1, p_saved.getId()),
        			() -> assertEquals("Roberta", p_saved.getNome())
        		);
    }
    
    @Test
    public void testDelete() {

        System.out.println("ProfessorDAO - test delete");
        
        Professor p = new Professor("Ailton");  
        Professor p_saved = dbTesting.inTransaction(() -> daoProfessor.persist(p));
    
        dbTesting.inTransaction(() -> daoProfessor.remove( p_saved ));

        assertNull( dbTesting.inTransaction(() -> daoProfessor.get( p_saved.getId() ))); 
    }
}
    