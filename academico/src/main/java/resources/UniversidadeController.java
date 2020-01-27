package resources;

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

import DAO.UniversidadeDAO;
import DTO.PersonDTO;
import DTO.UniversidadeDTO;
import br.ufal.ic.academico.model.Person;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("universidades")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class UniversidadeController {
	public final UniversidadeDAO UniversidadeDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll universits");
        
        return Response.ok(UniversidadeDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(UniversidadeDTO entity) {
        log.info("Universit - save: {}", entity);
        
        Universidade universidade = new Universidade(entity.getNome());
        universidade.setTelefone( entity.getTelefone() );
        
        return Response.ok(UniversidadeDAO.persist(universidade)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Universit - getById: id={}", id);
	        
	    Universidade universidade = UniversidadeDAO.get(id);
	    
	    
	    if( universidade != null) {
	    	return Response.ok(universidade).build();	
	    }
	    return Response.status(400).entity("Universidade com id = " + id+" não existe.").build();

    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, UniversidadeDTO entity) {
        
        log.info("University - update: id={}, {}", id, entity);
        
        Universidade universidade = UniversidadeDAO.get(id);
        
        if( universidade != null ) {
        	universidade.setNome( entity.getNome() );
        	universidade.setTelefone( entity.getTelefone() );
        	
        	return Response.ok(UniversidadeDAO.persist(  universidade )).build();
        }
        return Response.status(400).entity("Universidade com id = " + id+" não existe.").build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("delete: id={}", id);

        Universidade universidade = UniversidadeDAO.get(id);
        
        if( universidade != null) {
        	System.out.println( universidade.getNome() );
        	
        	UniversidadeDAO.remove(universidade);
        	
        	return Response.status(400).entity("Universidade com id = " + id + " removida.").build();
        }
        return Response.status(400).entity("Universidade com id = " + id+" não existe.").build();
    }
}
