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

import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Estudante;
import dao.CursoDAO;
import dao.EstudanteDAO;
import dto.EstudanteDTO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("estudantes")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class EstudanteController {
	public final EstudanteDAO estudanteDAO;
	public final CursoDAO cursoDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll students");
        
        return Response.ok(estudanteDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(EstudanteDTO entity) {
        log.info("Student - save: {}", entity);
        
        Curso curso = cursoDAO.get( entity.getId_curso() );
        Estudante estudante = new Estudante(entity.getNome(), curso);
        
        return Response.ok(estudanteDAO.persist(estudante)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Students - getById: id={}", id);
	    
	    Estudante estudante = estudanteDAO.get(id);
//	    estudante.getCurso().getDisciplinas().stream().forEach(s ->{
//	    	if( s.getEstudantes().contains(estudante)) {
//	    		
//	    	}
//	    });

        return Response.ok(estudante).build();
    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, EstudanteDTO entity) {
        
        log.info("Student - update: id={}, {}", id, entity);
        
        Estudante estudante = estudanteDAO.get(id);
        estudante.setScore( entity.getScore() );
        estudante.setPre_disciplinas( entity.getPre_disciplinas() );
        
        Curso curso = cursoDAO.get( entity.getId_curso() );
        estudante.setCurso(curso);

        return Response.ok(estudanteDAO.persist(  estudante )).build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Student - delete: id={}", id);

        Estudante estudante = estudanteDAO.get(id);
        System.out.println( estudante.getNome() );
        
        estudanteDAO.remove(estudante);
    
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
