package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CursoDAO<Curso> extends AbstractDAO<Curso> {

	public CursoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Curso get(Serializable id) throws HibernateException {
        log.info("getting curso: id={}", id);
        return super.get(id);
    }
    
    public List<Curso> list() throws HibernateException {
        log.info("getting course");
        return super.list(query("from Curso"));
    }
    
    @Override
    public Curso persist(Curso entity) throws HibernateException {
        return super.persist(entity);
    }
}
