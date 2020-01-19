package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
@Entity
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    
//    @OneToOne
//    @OneToMany
//    @ManyToMany
//    @ManyToOne
    
    
//    @ManyToOne
    @ManyToOne
    private Universidade universidade;

    public Departamento(String nome, Universidade universidade) {
        this.nome = nome;
        if ( universidade == null) {
            throw new IllegalArgumentException("Universidade não pode ser nulo ou vazio: '" + universidade + "'");
        }
        this.universidade = universidade;
    }
}
