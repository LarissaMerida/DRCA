package br.ufal.ic.academico;

import br.ufal.ic.academico.exemplos.MyResource;
import br.ufal.ic.academico.exemplos.Person;
import br.ufal.ic.academico.exemplos.PersonDAO;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcademicoApp extends Application<ConfigApp> {

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
    	
    	System.out.print("AAAAAA" +config+ environment);
        final PersonDAO dao = new PersonDAO(hibernate.getSessionFactory());
        log.info("RUn", dao);

        final MyResource resource = new MyResource(dao);
//        log.info("AAAAAA", resource);
//        int id3 = 0;
//        log.info("getMensage: id={}", id3);
//        Person p = new Person("lARISSA");
//        log.info("getID: id={}", p.getId());;;

        //resource.personDAO.persist(p);
        
        environment.jersey().register(resource);
        
        
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
