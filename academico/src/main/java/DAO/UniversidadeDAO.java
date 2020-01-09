package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UniversidadeDAO extends AbstractDAO<Universidade>{
	public UniversidadeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Universidade get(Serializable id) throws HibernateException {
        log.info("getting universidade: id={}", id);
        return super.get(id);
    }
    
    public List<Universidade> list() throws HibernateException {
        log.info("getting universidade");
        return super.list(query("from Universidade"));
    }
    
    @Override
    public Universidade persist(Universidade entity) throws HibernateException {
        return super.persist(entity);
    }
	
}
