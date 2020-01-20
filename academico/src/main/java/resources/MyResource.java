package resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.exemplos.Person;
import br.ufal.ic.academico.exemplos.PersonDAO;
import br.ufal.ic.academico.exemplos.PersonDTO;
import io.dropwizard.hibernate.UnitOfWork;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;


@Path("exemplos")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class MyResource {
    
    public final PersonDAO personDAO;
    
    @GET
    @UnitOfWork
    public Response getAll() {
        
        log.info("getAll");
        
        return Response.ok(personDAO.list()).build();
    }
    
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
//        
//        log.info("getById: id={}", id);
//        
//        Person p = personDAO.get(id);
  
         Person p2 = personDAO.get(id);
         log.info("getID: id={}", p2.getId());
 
        return Response.ok(p2).build();
    }
    
    @POST
    @Path("/test")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMensage() {
        //int id = 0;
        //log.info("getMensage: id={}", entity.getName() );
        Person p = new Person("lARISSA");
        p.setScore(1000);
        //log.info("getID: id={}", p.getId());

        //personDAO.persist(p);
        
        //PersonDTO p2 = new PersonDTO(personDAO.get(0));
        //log.info("getID: id={}", p2.getNumber());
        return Response.ok(personDAO.persist(p)).build();
    }

    @POST
    @UnitOfWork
    //@Consumes({"application/json"})
//    @Produces(MediaType.APPLICATION_JSON
    public Response save(PersonDTO entity) {
        log.info("getById: id={}",3234);
        log.info("save: {}", entity);
        
        Person p = new Person(entity.getName());
        p.setScore(entity.getNumber());
        //new StudentDTO(studentDAO.persist(s))).build();
        return Response.ok(personDAO.persist(p)).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @Consumes("application/json")
    public Response update(@PathParam("id") Long id, PersonDTO entity) {
        
        log.info("update: id={}, {}", id, entity);
        
        // TODO update
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("delete: id={}", id);
        
        // TODO delete
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    
}
