package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Estudante;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EstudanteDAO extends AbstractDAO<Estudante> {

	public EstudanteDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Estudante get(Serializable id) throws HibernateException {
        log.info("getting estudante: id={}", id);
        return super.get(id);
    }
    
    public List<Estudante> list() throws HibernateException {
        log.info("getting estudante");
        return super.list(query("from Estudante"));
    }
    
    @Override
    public Estudante persist(Estudante entity) throws HibernateException {
        return super.persist(entity);
    }
    
    public void remove(Estudante entity) throws HibernateException {
    	
    	log.info("deletting estudante: id={}",  entity.getId() );
 
    	super.currentSession().delete(entity);
    }
}
