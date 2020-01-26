package resources;

import java.awt.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DAO.DisciplinaDAO;
import DAO.EstudanteDAO;
import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("matricula")
@Slf4j
@RequiredArgsConstructor

@Produces(MediaType.APPLICATION_JSON)
public class MatriculaController {
	private final EstudanteDAO estudanteDAO;
	private final DisciplinaDAO disciplinaDAO;
	
	@GET
    @UnitOfWork
	public Response getEstudantes() {
		log.info("getAll students names");
   
        return Response.ok(estudanteDAO.list()).build();
	}

	@GET
	@Path("/{id_estudante}")
    @UnitOfWork
	public Response getDisciplinas(@PathParam("id_estudante") Long id_estudante) {
		log.info("getAll disciplinas");
		
		Estudante estudante = estudanteDAO.get(id_estudante);
		
        return Response.ok(estudante.getCurso().getDisciplinas()).build();
	}
	
	@GET
	@Path("/{id_estudante}/{id_disciplina}")
    @UnitOfWork
	public Response matricula(@PathParam("id_estudante") Long id_estudante, @PathParam("id_disciplina") Long id_disciplina) {
		log.info("matricula do aluno {} na disciplina {}",id_estudante, id_disciplina);
		
		Estudante estudante = estudanteDAO.get(id_estudante);
		Disciplina disciplina = disciplinaDAO.get(id_disciplina);
		
		if( disciplina.getEstudantes().contains(estudante) ) {
			
			return Response.status(400).entity("Aluno já matriculado anteriormente.").build();
		}
		if( estudante.getPre_disciplinas().contains(disciplina.getId()) ) {
			
			return Response.status(400).entity("Aluno já cursou disciplina.").build();
		}
		
		
		if( estudante.getCurso().getTipo() == Tipo.POS_GRADUACAO &&
					disciplina.getNivel() == Tipo.GRADUACAO) {
			return Response.status(400).entity("Aluno não matriculado por ser de pós-graduação e a disciplina ser de graduação.").build();
		}
		else if (  estudante.getCurso().getTipo() == Tipo.GRADUACAO && 
				disciplina.getNivel() == Tipo.POS_GRADUACAO &&
				estudante.getScore() >= 170) {
			disciplina.getEstudantes().add(estudante);
			estudante.getDisciplinas().add( disciplina );
			return Response.status(200).entity("Aluno matriculado.").build();
		}
		
		if( estudante.getScore() < disciplina.getMin_creditos()) {
			return Response.status(400).entity("Aluno não matriculado por c´redito mínimo insuficiente.").build();
		}
		if( estudante.getPre_disciplinas().containsAll( disciplina.getPre_disciplinas() ) == false) {
			return Response.status(400).entity("Necessário pré requisito de máteria.").build();
		}
		
//		if( estudante.getScore() >= disciplina.getMin_creditos() &&
//				estudante.getPre_disciplinas().containsAll( disciplina.getPre_disciplinas() )) {
		disciplina.getEstudantes().add(estudante);
		estudante.getDisciplinas().add( disciplina );
		return Response.status(200).entity("Aluno matriculado.").build();
//		}
//		/return Response.status(400).entity("Aluno matriculado.").build();
	}
	
}
