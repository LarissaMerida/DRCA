package br.ufal.ic.academico.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

import br.ufal.ic.academico.model.Secretaria.Tipo;
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
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Long creditos;
	private Long min_creditos;
	
	
	@Enumerated(EnumType.STRING)
    private DisciplinaTipo tipo;
	
	@Enumerated(EnumType.STRING)
    private Tipo nivel;
	
	@ElementCollection
    private List<Long> pre_disciplinas;
	
	@OneToOne
	private Professor professor;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Estudante> estudantes;
	
	public Disciplina( String nome, DisciplinaTipo tipo, Tipo nivel) {
		super();
		if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
		this.nome = nome;
		this.min_creditos = (long) 0;
		this.creditos = (long) 0;
		this.tipo = tipo;
		this.nivel = nivel;
		this.pre_disciplinas = new ArrayList<Long>();
		this.estudantes = new ArrayList<Estudante>();
	}
	
	

}
