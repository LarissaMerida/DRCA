package br.ufal.ic.academico.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author tarcisofilho
 */
public class UniversidadeTest {
    
    @Test
    public void testConstrutor() {
        assertThrows(IllegalArgumentException.class, () -> new Universidade(null));
        assertThrows(IllegalArgumentException.class, () -> new Universidade(""));
        
        Universidade u = new Universidade("UFAL");
        
        assertEquals("UFAL", u.getNome());
    }
}
