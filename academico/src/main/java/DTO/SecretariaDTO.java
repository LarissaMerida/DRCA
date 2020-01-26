package DTO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Secretaria.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class SecretariaDTO {
    
    @ManyToOne
    private Departamento departamento;
    
    private Long id_departamento;
    
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    
    private List<Long> cursos;
    
    
}