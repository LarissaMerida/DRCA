package resources;

import java.util.List;

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
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Disciplina;
import dao.CursoDAO;
import dao.DepartamentoDAO;
import dao.DisciplinaDAO;
import dto.CursoDTO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("cursos")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class CursoController {
	public final CursoDAO cursoDAO;
	public final DisciplinaDAO disciplinaDAO;
	public final DepartamentoDAO departamentoDAO;
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll courses");
        
        return Response.ok(cursoDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(CursoDTO entity) {
        log.info("Courses - save: {}", entity);
        
        Departamento d = departamentoDAO.get( entity.getId_departamento() );
        log.info("Departamento:", d);
        
        Curso curso = new Curso( entity.getNome(), entity.getTipo(), d );

        entity.getDisciplinas()
				.stream()
				.forEach(s -> {
					Disciplina disciplina = disciplinaDAO.get(s);
					
        			if( curso.getTipo() == disciplina.getNivel() ) {
        				curso.getDisciplinas().add( disciplina );
        			}
				});
        
        return Response.ok(cursoDAO.persist(curso)).build();
    }
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Courses - getById: id={}", id);
	        
	    Curso curso =  cursoDAO.get(id);

        return Response.ok(curso).build();
    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, CursoDTO entity) {
        
        log.info("Course - update: id={}, {}", id, entity);
        
        Curso curso = cursoDAO.get(id);
        curso.setTipo( entity.getTipo() );
        
        Departamento d = departamentoDAO.get( entity.getId_departamento() );
        curso.setDepartamento( d );
        
        curso.getDisciplinas().removeAll( curso.getDisciplinas() );
        
        entity.getDisciplinas()
			.stream()
			.forEach(s -> {
				Disciplina disciplina = disciplinaDAO.get(s);
				
				if( curso.getTipo() == disciplina.getNivel() ) {
					curso.getDisciplinas().add( disciplina );
				}
			});

       
        return Response.ok(cursoDAO.persist(  curso )).build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Course - delete: id={}", id);

        Curso curso = cursoDAO.get(id);
        System.out.println( curso.getNome() );
        
        cursoDAO.remove(curso);
    
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
