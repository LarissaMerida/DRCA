package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import br.ufal.ic.academico.model.Departamento;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DepartamentoDAO extends AbstractDAO<Departamento>{
	public DepartamentoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Departamento get(Serializable id) throws HibernateException {
        log.info("getting departamento: id={}", id);
        return super.get(id);
    }
    
    public List<Departamento> list() throws HibernateException {
        log.info("getting departamento");
        return super.list(query("from Departamento"));
    }
    
    @Override
    public Departamento persist(Departamento entity) throws HibernateException {
        return super.persist(entity);
    }
	
}
