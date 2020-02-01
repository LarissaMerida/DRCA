package dao;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.jersey.sessions.Session;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UniversidadeDAO extends AbstractDAO<Universidade>{
	public UniversidadeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Universidade get(Serializable id) throws HibernateException {
        log.info("getting universidade: id={}", id);
        Universidade universidade = super.get(id);
        
        if( universidade != null) {
        	return universidade;
        }
        return null;
    }
    
    public List<Universidade> list() throws HibernateException {
        log.info("getting universidade");
        return super.list(query("from Universidade"));
    }
    
    @Override
    public Universidade persist(Universidade entity) throws HibernateException {
        return super.persist(entity);
    }
    
    public void remove(Universidade entity)throws HibernateException {
    	log.info("deletting universidade: id={}", entity.getId());
 
    	super.currentSession().delete(entity);
    }
	
}
