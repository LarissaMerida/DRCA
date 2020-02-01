package integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.ConfigApp;
import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import ch.qos.logback.classic.Level;
import dao.CursoDAO;
import dao.DepartamentoDAO;
import dao.UniversidadeDAO;
import dto.CursoDTO;
import dto.DepartamentoDTO;
import dto.DisciplinaDTO;
import dto.EstudanteDTO;
import dto.ProfessorDTO;
import dto.SecretariaDTO;
import dto.UniversidadeDTO;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

@ExtendWith(DropwizardExtensionsSupport.class)
public class MatriculaControllerTest {
	
	private Universidade universidade_saved;
    private Departamento departamento_saved;
    private Professor professor_saved;
    private Disciplina disciplina_saved;
    private List<Disciplina> disciplinas;
    private Curso  curso_saved;
    private List<Curso> cursos;
    private Secretaria secretaria_saved;
    private Estudante estudante_saved;
    
	
	static {
        BootstrapLogging.bootstrap(Level.DEBUG);
    }

    public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension(AcademicoApp.class,
            ResourceHelpers.resourceFilePath("config-test.yml"));
    
    @BeforeEach
    @SneakyThrows
    public void setUp() {
    	System.out.println("testMatricula - inicializando ");
    	
    	UniversidadeDTO universidade = new UniversidadeDTO();
    	universidade.setNome("UFAL");
    	universidade_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/universidades", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(universidade), Universidade.class);
    	
    	DepartamentoDTO departamento = new DepartamentoDTO("Instituto de Computação", universidade_saved, universidade_saved.getId());
    	departamento_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/departamentos", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(departamento), Departamento.class);
    	
    	ProfessorDTO professor = new ProfessorDTO("Alcino");
    	professor_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/professores", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(professor), Professor.class);
    	
    	DisciplinaDTO disciplina = new DisciplinaDTO();
    	disciplina.setNome("Compiladores");
    	disciplina.setTipo(DisciplinaTipo.OBRIGATORIA);
    	disciplina.setId_professor( (long) professor_saved.getId() ); 
    	disciplina.setNivel(Tipo.GRADUACAO);
    	disciplina.setTipo(DisciplinaTipo.OBRIGATORIA);
    	
    	disciplina_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/disciplinas", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(disciplina), Disciplina.class);
    	
    	disciplinas = new ArrayList<Disciplina>();
    	disciplinas.add(disciplina_saved);
    	
    	CursoDTO curso = new CursoDTO();
    	curso.setId_departamento( departamento_saved.getId() );
    	curso.setNome("Ciência da Computação");
    	curso.setTipo(Tipo.GRADUACAO);
    	List<Long> id_disicplinas = disciplinas.stream().map(s -> s.getId()).collect(Collectors.toList());
    	curso.setDisciplinas(id_disicplinas);
    	
  
    	curso_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/cursos", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(curso), Curso.class);
    	
    	cursos = new ArrayList<Curso>();
    	cursos.add(curso_saved);
    	SecretariaDTO secretaria = new SecretariaDTO();
    	secretaria.setId_departamento(departamento_saved.getId());
    	secretaria.setTipo(Tipo.GRADUACAO);
    	List<Long> id_cursos = cursos.stream().map(s -> s.getId()).collect(Collectors.toList());
    	secretaria.setCursos(id_cursos);
    	
    	secretaria_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/secretarias", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(secretaria), Secretaria.class);
    	
    	EstudanteDTO estudante = new EstudanteDTO();	
    	estudante.setId_curso( curso_saved.getId() );
    	estudante.setNome("Nick");
    	
    	estudante_saved = RULE.client().target(
                String.format("http://localhost:%d/%s/estudantes", RULE.getLocalPort(), "academicotest"))
               .request()
               .post(Entity.json(estudante), Estudante.class);
    	
    }
    
    
    @Test
    public void testMatricula() {
    	
    	System.out.println("testMatricula");
    	List<Estudante> estudantes = RULE.client().target(
                 String.format("http://localhost:%d/%s/matricula", RULE.getLocalPort(), "academicotest"))
                .request()
                .get(new GenericType<List<Estudante>>() {});
  
    	assertNotNull( estudantes );
    	 
    	System.out.println("Estudante:" + estudante_saved.getId());
    	 
    	List<Disciplina> list_disciplinas = RULE.client().target(
                 String.format("http://localhost:%d/%s/matricula/%d", 
                		 RULE.getLocalPort(), "academicotest",  estudantes.get(0).getId() ))
                .request()
                .get(new GenericType<List<Disciplina>>() {});
    
    	 assertNotNull( list_disciplinas  );
    	 
    	// Matricular em disciplina de pós graduação sendo graduando e score > 170
    	 estudantes.get(0).setScore((long) 180);
    	 Response response4 = RULE.client().target(
                 String.format("http://localhost:%d/%s/matricula/%d/%d", 
                		 RULE.getLocalPort(), "academicotest", estudantes.get(0).getId(), list_disciplinas.get(0).getId() ))
                .request()
                .get(new GenericType<Response>() {});
    	 assertEquals(200, response4.getStatus() );
    	 
    	 
    	 list_disciplinas.get(0).setEstudantes( new ArrayList<Estudante>());
    	 estudantes.get(0).setDisciplinas(new ArrayList<Disciplina>());
    	 
    	 // Matricular correto
    	 
    	DisciplinaDTO disciplina = new DisciplinaDTO();
     	disciplina.setNome("Compiladores");
     	disciplina.setTipo(DisciplinaTipo.OBRIGATORIA);
     	disciplina.setId_professor( (long) professor_saved.getId() ); 
     	disciplina.setNivel(Tipo.GRADUACAO);
     	disciplina.setTipo(DisciplinaTipo.OBRIGATORIA);
     	
     	Disciplina disciplina_saved2 = RULE.client().target(
                 String.format("http://localhost:%d/%s/disciplinas", RULE.getLocalPort(), "academicotest"))
                .request()
                .post(Entity.json(disciplina), Disciplina.class);
     	
     	
    	 Response response = RULE.client().target(
                 String.format("http://localhost:%d/%s/matricula/%d/%d", 
                		 RULE.getLocalPort(), "academicotest", estudantes.get(0).getId(), disciplina_saved2.getId() ))
                .request()
                .get(new GenericType<Response>() {});
    	 
    	 assertEquals(200, response.getStatus() );
    	 
    	 
    	 //Rematricular novamente aluno
    	 Response response2 = RULE.client().target(
                 String.format("http://localhost:%d/%s/matricula/%d/%d", 
                		 RULE.getLocalPort(), "academicotest", estudantes.get(0).getId(), disciplina_saved2.getId() ))
                .request()
                .get(new GenericType<Response>() {});
    	 assertEquals(400, response2.getStatus() );

    }
    
}
