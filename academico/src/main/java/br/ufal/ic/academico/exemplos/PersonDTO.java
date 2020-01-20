package br.ufal.ic.academico.exemplos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class PersonDTO {
    
    private String name;
    private int number;
    
//    public PersonDTO(Person p) {
//    	this.name = p.getName();
//    	this.number = p.getId();
//    };;
    
}