package br.ufal.ic.academico;

import DAO.CursoDAO;
import DAO.DepartamentoDAO;
import DAO.DisciplinaDAO;
import DAO.PersonDAO;
import DAO.ProfessorDAO;
import DAO.SecretariaDAO;
import DAO.UniversidadeDAO;
import br.ufal.ic.academico.exemplos.Person;
import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Secretaria.SecretariaTipo;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import resources.CursoController;
import resources.DepartamentoController;
import resources.DisciplinaController;
import resources.MyResource;
import resources.ProfessorController;
import resources.SecretariaController;
import resources.UniversidadeController;

@Slf4j
public class AcademicoApp extends Application<ConfigApp> {
	private SecretariaTipo tipo;
	
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


        final MyResource resource = new MyResource(dao);
        final UniversidadeController universidadeController = new UniversidadeController(universidadeDao);
        final DepartamentoController departamentoController = new DepartamentoController(departamentoDao, universidadeDao);
        final SecretariaController secretariaController = new SecretariaController(departamentoDao, secretariaDAO );
        final DisciplinaController disciplinaController = new DisciplinaController(disciplinaDAO );
        final ProfessorController professorController = new ProfessorController(professorDAO );
        final CursoController cursoController = new CursoController(cursoDAO);
        
        log.info("AAAAAA", resource);
        
        environment.jersey().register(resource);
        environment.jersey().register(universidadeController);
        environment.jersey().register(departamentoController);  
        environment.jersey().register(secretariaController);  
        environment.jersey().register(disciplinaController); 
        environment.jersey().register(cursoController); 
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(Person.class, Universidade.class,
                    Departamento.class, Secretaria.class, Disciplina.class, Professor.class, Curso.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}
