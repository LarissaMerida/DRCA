package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	
	public Curso( String nome) {
		if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
		this.nome = nome;
	}
}
