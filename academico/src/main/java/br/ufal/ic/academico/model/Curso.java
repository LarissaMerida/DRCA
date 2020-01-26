package br.ufal.ic.academico.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	
	@Enumerated(EnumType.STRING)
    private Tipo tipo;
	
	@OneToMany(cascade = CascadeType.ALL)
    private List<Disciplina> disciplinas;
	
	@ManyToOne
    private Departamento departamento;
	
	public Curso( String nome, Tipo tipo, Departamento departamento) {
		if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
		this.nome = nome;
		this.tipo = tipo;
		
		if (departamento == null) {
            throw new NullPointerException("Departamento não pode ser nulo.");
        }
		this.departamento = departamento;
		this.disciplinas = new ArrayList<Disciplina>();
	}
}
