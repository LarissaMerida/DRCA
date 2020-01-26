package br.ufal.ic.academico.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Estudante extends Person {
    private Long score;
    
    @OneToOne
    private Curso curso;
    @ElementCollection
    private List<Long> pre_disciplinas;
    
    @ElementCollection
    private List<Disciplina> disciplinas;
    

    public Estudante(String nome, Curso curso) {
    	super(nome);
        this.score = (long) 0;
        this.pre_disciplinas = new ArrayList<Long>();
        this.disciplinas = new ArrayList<Disciplina>();
        
        if (curso == null) {
            throw new NullPointerException("Curso não pode ser nulo.");
        }
        this.curso = curso;
    }
}
