package DTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Estudante;
import br.ufal.ic.academico.model.Professor;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import br.ufal.ic.academico.model.Disciplina.DisciplinaTipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class DisciplinaDTO {
    
    private String nome;
    
	private Long creditos;
	private Long min_creditos;
	
	@Enumerated(EnumType.STRING)
    private DisciplinaTipo tipo;
	
	@Enumerated(EnumType.STRING)
    private Tipo nivel;
	
	@ElementCollection
	private List<Long> pre_disciplinas =  Arrays.asList();
	
	@OneToOne
	private Professor professor;
	
	private Long id_professor;
	
}