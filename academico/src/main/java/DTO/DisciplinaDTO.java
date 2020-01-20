package DTO;

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
public  class DisciplinaDTO {
    
    private String nome;
    
	private Long creditos;
	private Long min_creditos;
    
//    public PersonDTO(Person p) {
//    	this.name = p.getName();
//    	this.number = p.getId();
//    };;
    
}