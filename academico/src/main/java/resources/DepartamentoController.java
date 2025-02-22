package resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Universidade;
import dao.DepartamentoDAO;
import dao.UniversidadeDAO;
import dto.DepartamentoDTO;
import dto.UniversidadeDTO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("departamentos")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class DepartamentoController {
	public final DepartamentoDAO departamentoDAO;
	public final UniversidadeDAO universidadeDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll departments");
        
        return Response.ok(departamentoDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(DepartamentoDTO entity) {
        log.info("Department - save: {}", entity);
        
        Universidade u = universidadeDAO.get( entity.getId_universidade() );
        
        Departamento departamento = new Departamento( entity.getNome(), u);
        
        return Response.ok(departamentoDAO.persist(departamento)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Department - getById: id={}", id);
	        
	    Departamento departamento = departamentoDAO.get(id);
	    
	    if( departamento != null) {
	    	return Response.ok(departamento).build();	    	
	    }
	    return Response.status(400).entity("Departamento com id = " + id+" não existe.").build();

    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, DepartamentoDTO entity) {
        
        log.info("Department - update: id={}, {}", id, entity);
        
        Universidade u = universidadeDAO.get( entity.getId_universidade() );
        
        Departamento departamento = departamentoDAO.get(id);
        
        if( departamento != null) {
        	departamento.setNome( entity.getNome() );
        	departamento.setUniversidade( u );
        	
        	return Response.ok(departamentoDAO.persist(  departamento )).build();
        }
        return Response.status(400).entity("Departamento com id = " + id+" não existe.").build();
        
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Department - delete: id={}", id);

        Departamento departamento = departamentoDAO.get(id);
        
        if( departamento != null) {
        	System.out.println( departamento.getNome() );
        	
        	departamentoDAO.remove(departamento);        	
        	
        	return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(400).entity("Departamento com id = " + id+" não existe.").build();
    }
}
