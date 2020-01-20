package resources;

import javax.ws.rs.Path;

import DAO.EstudanteDAO;
import DAO.UniversidadeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("estudantes")
@Slf4j
@RequiredArgsConstructor
public class EstudanteController {
	public final EstudanteDAO estudanteDAO;
}
