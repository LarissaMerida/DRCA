package DTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Disciplina;
import br.ufal.ic.academico.model.Disciplina.DisciplinaNivel;
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
    private DisciplinaNivel nivel;
    
	private List<Long> ids_pre_disciplinas =  Arrays.asList();
	
	@ManyToMany
    private List<Disciplina> pre_disciplinas;
}