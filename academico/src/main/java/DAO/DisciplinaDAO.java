package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Disciplina;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DisciplinaDAO extends AbstractDAO<Disciplina>{
	public DisciplinaDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Disciplina get(Serializable id) throws HibernateException {
        log.info("getting disciplina: id={}", id);
        return super.get(id);
    }
    
    public List<Disciplina> list() throws HibernateException {
        log.info("getting disciplina");
        return super.list(query("from Disciplina"));
    }
    
    @Override
    public Disciplina persist(Disciplina entity) throws HibernateException {
        return super.persist(entity);
    }
	
}
