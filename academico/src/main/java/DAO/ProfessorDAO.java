package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ProfessorDAO extends AbstractDAO<Professor>{
	public ProfessorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Professor get(Serializable id) throws HibernateException {
        log.info("getting professor: id={}", id);
        Professor professor = super.get(id);
        
        if( professor != null) {
        	return professor;
        }
        return null;
    }
    
    public List<Professor> list() throws HibernateException {
        log.info("getting professor");
        return super.list(query("from Professor"));
    }
    
    @Override
    public Professor persist(Professor entity) throws HibernateException {
        return super.persist(entity);
    }
	
    public void remove(Professor entity)throws HibernateException {
    	log.info("deletting professor: id={}", entity.getId());
 
    	super.currentSession().delete(entity);
    }
}
