package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.DisciplinaDAO;
import DTO.DisciplinaDTO;
import br.ufal.ic.academico.model.Disciplina;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("disciplinas")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class DisciplinaController {
	public final DisciplinaDAO disciplinaDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll subjects");
        
        return Response.ok(disciplinaDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(DisciplinaDTO entity) {
        log.info("Subjects - save: {}", entity);
        
        Disciplina disciplina = new Disciplina(entity.getNome(), entity.getTipo(), entity.getNivel());
        
        if( entity.getCreditos() != null ) {
        	disciplina.setCreditos( entity.getCreditos() );	
        }
        else {
        	disciplina.setCreditos( (long) 0 );
        }
        
        entity.getIds_pre_disciplinas().stream()
        								.forEach(  s -> disciplina.getPre_disciplinas().add( disciplinaDAO.get( s))  );
        
        return Response.ok(disciplinaDAO.persist(disciplina)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Subject - getById: id={}", id);
	        
	    Disciplina disciplina = disciplinaDAO.get(id);

        return Response.ok(disciplina).build();
    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, DisciplinaDTO entity) {
        
        log.info("Subject - update: id={}, {}", id, entity);
        
        Disciplina disciplina = disciplinaDAO.get(id);
        
        if( entity.getCreditos() != null ) {
        	disciplina.setCreditos( entity.getCreditos() );	
        }
        else {
        	disciplina.setCreditos( (long) 0 );
        }
        
        entity.getIds_pre_disciplinas().stream()
        								.forEach(  s -> disciplina.getPre_disciplinas().add( disciplinaDAO.get( s))  );
      
        return Response.ok(disciplinaDAO.persist(  disciplina )).build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Subjects] - delete: id={}", id);

        Disciplina disciplina = disciplinaDAO.get(id);
        System.out.println( disciplina.getNome() );
        
        disciplinaDAO.remove(disciplina);
    
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
