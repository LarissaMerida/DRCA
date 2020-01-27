package br.ufal.ic.academico;

import DAO.CursoDAO;
import DAO.DepartamentoDAO;
import DAO.DisciplinaDAO;
import DAO.EstudanteDAO;
import DAO.PersonDAO;
import DAO.ProfessorDAO;
import DAO.SecretariaDAO;
import DAO.UniversidadeDAO;
import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Person;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import resources.CursoController;
import resources.DepartamentoController;
import resources.DisciplinaController;
import resources.EstudanteController;
import resources.MatriculaController;
import resources.MyResource;
import resources.ProfessorController;
import resources.SecretariaController;
import resources.UniversidadeController;

@Slf4j
public class AcademicoApp extends Application<ConfigApp> {
	private Tipo tipo;
	
    public static void main(String[] args) throws Exception {
        new AcademicoApp().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ConfigApp> bootstrap) {
        log.info("initialize");
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(ConfigApp config, Environment environment) {
    	
        final PersonDAO dao = new PersonDAO(hibernate.getSessionFactory());
        final UniversidadeDAO universidadeDao = new UniversidadeDAO(hibernate.getSessionFactory());
        final DepartamentoDAO departamentoDao = new DepartamentoDAO(hibernate.getSessionFactory());
        final SecretariaDAO secretariaDAO = new SecretariaDAO(hibernate.getSessionFactory());
        final DisciplinaDAO disciplinaDAO = new DisciplinaDAO(hibernate.getSessionFactory());
        final ProfessorDAO professorDAO = new ProfessorDAO(hibernate.getSessionFactory());
        final CursoDAO cursoDAO = new CursoDAO(hibernate.getSessionFactory());
        final EstudanteDAO estudanteDAO = new EstudanteDAO(hibernate.getSessionFactory());


        final MyResource resource = new MyResource(dao);
        final UniversidadeController universidadeController = new UniversidadeController(universidadeDao);
        final DepartamentoController departamentoController = new DepartamentoController(departamentoDao, universidadeDao);
        final CursoController cursoController = new CursoController(cursoDAO,disciplinaDAO, departamentoDao);
        final SecretariaController secretariaController = new SecretariaController(departamentoDao, secretariaDAO , cursoDAO);
        final ProfessorController professorController = new ProfessorController(professorDAO );
        final DisciplinaController disciplinaController = new DisciplinaController(disciplinaDAO ,professorDAO);
        final EstudanteController estudanteController = new EstudanteController(estudanteDAO, cursoDAO);
        final MatriculaController matriculaController = new MatriculaController(estudanteDAO, disciplinaDAO);
        
        environment.jersey().register(resource);
        environment.jersey().register(universidadeController);
        environment.jersey().register(departamentoController);  
        environment.jersey().register(secretariaController);  
        environment.jersey().register(disciplinaController); 
        environment.jersey().register(professorController); 
        environment.jersey().register(cursoController); 
        environment.jersey().register(estudanteController); 
        environment.jersey().register(matriculaController); 
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(Person.class, Universidade.class,
                    Departamento.class, Secretaria.class, Disciplina.class, Professor.class, 
                    Curso.class, Estudante.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}
