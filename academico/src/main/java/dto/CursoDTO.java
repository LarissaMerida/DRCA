package dto;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Universidade;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class CursoDTO {
    
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    private List<Long> disciplinas;
    
    @ManyToOne
    private Departamento departamento;
    
    private Long id_departamento;
    
}