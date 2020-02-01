package dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import br.ufal.ic.academico.model.Curso;

@Slf4j
public class CursoDAO extends AbstractDAO<Curso> {

	public CursoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Curso get(Serializable id) throws HibernateException {
        log.info("getting curso: id={}", id);
        
        Curso curso = super.get(id);
        if(curso != null) {
        	return curso;
        }
        return null;
    }
    
    public List<Curso> list() throws HibernateException {
        log.info("getting course");
        return super.list(query("from Curso"));
    }
    
    @Override
    public Curso persist(Curso entity) throws HibernateException {
        return super.persist(entity);
    }
    
    public void remove(Curso entity) throws HibernateException {
    	
    	log.info("deletting course: id={}",  ((br.ufal.ic.academico.model.Curso) entity).getId() );
 
    	super.currentSession().delete(entity);
    }
}
