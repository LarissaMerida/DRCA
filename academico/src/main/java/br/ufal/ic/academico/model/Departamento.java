package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    
    @ManyToOne
    private Universidade universidade;

    public Departamento(String nome, Universidade universidade) {
        if ( StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Universidade não pode ser nulo ou vazio: '" + universidade + "'");
        }
        this.nome = nome;
        this.universidade = universidade;
    }
}
