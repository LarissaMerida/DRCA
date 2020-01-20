package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@RequiredArgsConstructor
public class Estudante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String nome;
    
    @Setter private int score;

    public Estudante(String nome) {
    	if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
        this.nome = nome;
    }
}
