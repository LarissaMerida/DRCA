package br.ufal.ic.academico;

import DAO.DepartamentoDAO;
import DAO.PersonDAO;
import DAO.SecretariaDAO;
import DAO.UniversidadeDAO;
import br.ufal.ic.academico.exemplos.Person;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Secretaria.SecretariaTipo;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import resources.DepartamentoController;
import resources.MyResource;
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


        final MyResource resource = new MyResource(dao);
        final UniversidadeController universidadeController = new UniversidadeController(universidadeDao);
        final DepartamentoController departamentoController = new DepartamentoController(departamentoDao, universidadeDao);
        final SecretariaController secretariaController = new SecretariaController(departamentoDao, secretariaDAO );
        
        log.info("AAAAAA", resource);
        
        environment.jersey().register(resource);
        environment.jersey().register(universidadeController);
        environment.jersey().register(departamentoController);  
        environment.jersey().register(secretariaController);  
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(Person.class, Universidade.class,
                    Departamento.class, Secretaria.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}
