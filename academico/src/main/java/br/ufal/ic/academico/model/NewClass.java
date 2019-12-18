package br.ufal.ic.academico.model;

import static br.ufal.ic.academico.model.Secretaria.SecretariaTipo.GRADUACAO;

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
