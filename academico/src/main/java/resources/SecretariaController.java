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

import DAO.CursoDAO;
import DAO.DepartamentoDAO;
import DAO.SecretariaDAO;
import DTO.DepartamentoDTO;
import DTO.SecretariaDTO;
import DTO.UniversidadeDTO;
import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("secretarias")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class SecretariaController {
	public final DepartamentoDAO departamentoDAO;
//	public final SecretariaTipo tipo;
	public final SecretariaDAO secretariaDAO;
	public final CursoDAO cursoDAO;
	
	
	@GET
    @UnitOfWork
	public Response getAll() {
		log.info("getAll secretariats");
        
        return Response.ok(secretariaDAO.list()).build();
	}
	
	@POST
    @UnitOfWork
    public Response save(SecretariaDTO entity) {
        log.info("Secretary - save: {}", entity);
        
        Departamento d = departamentoDAO.get( entity.getId_departamento() );
        log.info("Departamento:", d);
        
        Secretaria secretaria = new Secretaria( d, entity.getTipo());
        
        entity.getCursos()
        		.stream()
        		.forEach(s -> {
        			Curso curso = cursoDAO.get(s);
        			if( secretaria.getTipo() == curso.getTipo() ) {
        				secretaria.getCursos().add( curso );
        			}
        		});
        
        System.out.println( entity.getCursos() );
        return Response.ok(secretariaDAO.persist(secretaria)).build();
	}
	
	@GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
	        
	    log.info("Secretary - getById: id={}", id);
	        
	    Secretaria secretaria = secretariaDAO.get(id);

        return Response.ok(secretaria).build();
    }
	
	@PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@PathParam("id") Long id, SecretariaDTO entity) {
        
        log.info("Secretary - update: id={}, {}", id, entity);
        
        Departamento d = departamentoDAO.get( entity.getId_departamento() );
        
        Secretaria secretaria = secretariaDAO.get(id);
        secretaria.setDepartamento( d );
        secretaria.setTipo( entity.getTipo() );
        
        secretaria.getCursos().removeAll(secretaria.getCursos());
        
        entity.getCursos()
				.stream()
				.forEach(s -> {
					Curso curso = cursoDAO.get(s);
					if( secretaria.getTipo() == curso.getTipo() ) {
						secretaria.getCursos().add( curso );
					}
				});

        return Response.ok(secretariaDAO.persist(  secretaria )).build();
    }
	
	@DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("Secretary - delete: id={}", id);

        Secretaria secretaria = secretariaDAO.get(id);
        System.out.println( secretaria.getId() );
        
        secretariaDAO.remove(secretaria);
    
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
