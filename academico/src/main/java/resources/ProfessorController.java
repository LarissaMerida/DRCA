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

import DAO.ProfessorDAO;
import DTO.ProfessorDTO;
import br.ufal.ic.academico.model.Professor;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("professores")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class ProfessorController {
	public final ProfessorDAO professorDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll teachers");
        
        return Response.ok(professorDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(ProfessorDTO entity) {
        log.info("Teacher - save: {}", entity);
        
        Professor professor = new Professor(entity.getNome());
        
        return Response.ok(professorDAO.persist(professor)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Teachers - getById: id={}", id);
	        
	    Professor professor = professorDAO.get(id);

        return Response.ok(professor).build();
    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, ProfessorDTO entity) {
        
        log.info("Teacher - update: id={}, {}", id, entity);
        
        Professor professor = professorDAO.get(id);

        return Response.ok(professorDAO.persist(  professor )).build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Teacher - delete: id={}", id);

        Professor professor =  professorDAO.get(id);
        System.out.println( professor.getNome() );
        
        professorDAO.remove(professor);
    
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
