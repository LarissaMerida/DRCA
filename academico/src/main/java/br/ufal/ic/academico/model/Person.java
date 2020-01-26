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
@Setter
@Getter
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String nome;

    public Person(String nome) {
    	if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
        this.nome = nome;
    }
}
