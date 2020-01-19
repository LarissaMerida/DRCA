package DTO;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Departamento;
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
    
    private String name;
    
    @ManyToOne
    private Departamento departamento;
    
//    public PersonDTO(Person p) {
//    	this.name = p.getName();
//    	this.number = p.getId();
//    };;
    
}