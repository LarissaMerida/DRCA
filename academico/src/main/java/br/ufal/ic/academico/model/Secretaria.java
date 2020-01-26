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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
//@ToString(of = { "id", "tipo" })
@EqualsAndHashCode(of = "id")
public class Secretaria {

    public enum Tipo {
        GRADUACAO, POS_GRADUACAO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Departamento departamento;
    
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Curso> cursos;

    public Secretaria(Departamento departamento, Tipo tipo) {
        this.departamento = departamento;
        this.tipo = tipo;
        this.cursos = new ArrayList<Curso>();
    }    
}
