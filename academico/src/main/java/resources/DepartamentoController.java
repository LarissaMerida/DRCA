package resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.DepartamentoDAO;
import DAO.UniversidadeDAO;
import DTO.DepartamentoDTO;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Universidade;
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
        
        log.info("I@@@@@@@D" +entity.getUniversidade());
        
        //UniversidadeDAO universidade = null;
        //Universidade u = universidade.get( entity.getUniversidade() );
        Universidade u = universidadeDAO.get( entity.getUniversidade() );
        //Departamento departamento = new Departamento(entity.getNome(), u);
          System.out.println("UNDIDI" + u.getId());
        //return Response.ok(departamentoDAO.persist(departamento)).build();
        return Response.ok().build();
    }
}
