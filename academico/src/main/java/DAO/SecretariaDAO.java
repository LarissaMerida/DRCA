package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SecretariaDAO extends AbstractDAO<Secretaria>{
	public SecretariaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Secretaria get(Serializable id) throws HibernateException {
        log.info("getting secretaria: id={}", id);
        
        Secretaria secretaria = super.get(id);
        if( secretaria != null) {
        	return secretaria;
        }
        return null;
    }
    
    public List<Secretaria> list() throws HibernateException {
        log.info("getting secretaria");
        return super.list(query("from Secretaria"));
    }
    
    @Override
    public Secretaria persist(Secretaria entity) throws HibernateException {
        return super.persist(entity);
    }
    
    public void remove(Secretaria entity)throws HibernateException {
    	log.info("deletting secretary: id={}", entity.getId());
 
    	super.currentSession().delete(entity);
    }
}
