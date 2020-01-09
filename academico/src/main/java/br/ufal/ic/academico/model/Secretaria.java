package br.ufal.ic.academico.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@Entity
@Getter
@ToString(of = { "id", "tipo" })
@EqualsAndHashCode(of = "id")
public class Secretaria {

    public enum SecretariaTipo {
        GRADUACAO, POS_GRADUACAO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Departamento departamento;
    
    @Enumerated(EnumType.STRING)
    private SecretariaTipo tipo;

    public Secretaria(Departamento departamento, SecretariaTipo tipo) {
        this.departamento = departamento;
        this.tipo = tipo;
    }    
}
