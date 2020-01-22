package br.ufal.ic.academico.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Disciplina {
	
	public enum DisciplinaTipo {
        OBRIGATORIA, ELETIVA
    }
	
	public enum DisciplinaNivel {
		 GRADUACAO, POS_GRADUACAO
    }
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Long creditos;
	
	
	@Enumerated(EnumType.STRING)
    private DisciplinaTipo tipo;
	
	@Enumerated(EnumType.STRING)
    private DisciplinaNivel nivel;
	
	@ManyToMany
    private List<Disciplina> pre_disciplinas;
	
	public Disciplina( String nome, DisciplinaTipo tipo, DisciplinaNivel nivel) {
		super();
		if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
		this.nome = nome;
		this.creditos = (long) 0;
		this.tipo = tipo;
		this.nivel = nivel;
		this.pre_disciplinas = new ArrayList<Disciplina>();
	}
	
	

}
