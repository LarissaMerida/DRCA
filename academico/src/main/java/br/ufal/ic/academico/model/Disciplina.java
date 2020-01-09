package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class Disciplina {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Long creditos;
	private Long min_creditos;
	
	public Disciplina(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	

}
