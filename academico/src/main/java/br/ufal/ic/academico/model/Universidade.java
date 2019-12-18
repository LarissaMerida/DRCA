package br.ufal.ic.academico.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author tarcisofilho
 */
@Getter
@Entity
@RequiredArgsConstructor
public class Universidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String telefone;
//    @OneToMany private List<Departamento> departamentos; - evitar
    
    public Universidade(String nome) {
        if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("nome não pode ser nulo ou vazio: '" + nome + "'");
        }
        this.nome = nome;
    }
    
}
