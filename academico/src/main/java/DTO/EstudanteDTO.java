package DTO;

import java.util.List;

import javax.persistence.ElementCollection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.ufal.ic.academico.model.Curso;
import br.ufal.ic.academico.model.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class EstudanteDTO {
    
    private String nome;
    private Long score;
    
    @ElementCollection
    private List<Long> pre_disciplinas;
    
    @ElementCollection
    private List<Disciplina> disciplinas;
    
    private Curso curso;
    
    private Long id_curso;
    
}