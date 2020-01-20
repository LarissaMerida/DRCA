package br.ufal.ic.academico.exemplos;

import static br.ufal.ic.academico.model.Secretaria.SecretariaTipo.GRADUACAO;

import br.ufal.ic.academico.model.Departamento;
import br.ufal.ic.academico.model.Secretaria;
import br.ufal.ic.academico.model.Universidade;

/**
 *
 * @author tarcisofilho
 */
public class NewClass {

    public static void main(String[] args) {
        Universidade u1 = new Universidade(null);
        Departamento ic = new Departamento("IC", u1);
        
        Secretaria s = new Secretaria(ic, GRADUACAO);
    }
}
