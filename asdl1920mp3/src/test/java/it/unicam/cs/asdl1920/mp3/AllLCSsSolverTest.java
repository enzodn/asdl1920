/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Classe di unit test per il risolutore del problema di trovare tutte le più
 * lunghe sottosequenze comuni di due stringhe date.
 * 
 * @author Luca Tesei
 *
 */
class AllLCSsSolverTest {

    @Test
    final void testGetLengthOfSolutions1() {
        AllLCSsSolver s0 = new AllLCSsSolver("ABCBDAB", "BDCABA");
        s0.solve();
        assertTrue(s0.getLengthOfSolutions() == 4);
        AllLCSsSolver s2 = new AllLCSsSolver("XMJYAUZ", "MZJAWXU");
        s2.solve();
        assertTrue(s2.getLengthOfSolutions() == 4);
        AllLCSsSolver s3 = new AllLCSsSolver("XYZ", "ABCDS");
        s3.solve();
        assertTrue(s3.getLengthOfSolutions() == 0);
        AllLCSsSolver s4 = new AllLCSsSolver("AB", "AB");
        s4.solve();
        assertTrue(s4.getLengthOfSolutions() == 2);
        AllLCSsSolver s5 = new AllLCSsSolver("123412", "123412123412");
        s5.solve();
        assertTrue(s5.getLengthOfSolutions() == 6);
    }

    @Test
    final void testGetSolutions1() {
        AllLCSsSolver s0 = new AllLCSsSolver("ABCBDAB", "BDCABA");
        s0.solve();
        Set<String> sol0 = new HashSet<String>();
        assertFalse(s0.getSolutions().equals(sol0));
        sol0.add("BDAB");
        assertFalse(s0.getSolutions().equals(sol0));
        sol0.add("BCAB");
        assertFalse(s0.getSolutions().equals(sol0));
        sol0.add("BCBA");
        assertTrue(s0.getSolutions().equals(sol0));
        AllLCSsSolver s2 = new AllLCSsSolver("XMJYAUZ", "MZJAWXU");
        s2.solve();
        Set<String> sol2 = new HashSet<String>();
        assertFalse(s2.getSolutions().equals(sol2));
        sol2.add("MJAU");
        assertTrue(s2.getSolutions().equals(sol2));
        AllLCSsSolver s3 = new AllLCSsSolver("XYZ", "ABCDS");
        s3.solve();
        Set<String> sol3 = new HashSet<String>();
        assertFalse(s3.getSolutions().equals(sol3));
        sol3.add("");
        assertTrue(s3.getSolutions().equals(sol3));
        AllLCSsSolver s4 = new AllLCSsSolver("AB", "AB");
        s4.solve();
        Set<String> sol4 = new HashSet<String>();
        assertFalse(s4.getSolutions().equals(sol4));
        sol4.add("AB");
        assertTrue(s4.getSolutions().equals(sol4));
        AllLCSsSolver s5 = new AllLCSsSolver("123412", "123412123412");
        s5.solve();
        Set<String> sol5 = new HashSet<String>();
        assertFalse(s5.getSolutions().equals(sol5));
        sol5.add("123412");
        assertTrue(s5.getSolutions().equals(sol5));
    }

    @Test
    final void testLengthOfSolutions2() {
        AllLCSsSolver s1 = new AllLCSsSolver("abcabcaa", "acbacba");
        s1.solve();
        assertTrue(s1.getLengthOfSolutions() == 5);
        AllLCSsSolver s6 = new AllLCSsSolver("2134", "1243");
        s6.solve();
        assertTrue(s6.getLengthOfSolutions() == 2);
    }

    @Test
    final void testGetSolutions2() {
        AllLCSsSolver s1 = new AllLCSsSolver("abcabcaa", "acbacba");
        s1.solve();
        Set<String> sol1 = new HashSet<String>();
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("acbaa");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("abcba");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("acaca");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("abaca");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("acaba");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("acbca");
        assertFalse(s1.getSolutions().equals(sol1));
        sol1.add("ababa");
        assertTrue(s1.getSolutions().equals(sol1));
        AllLCSsSolver s6 = new AllLCSsSolver("2134", "1243");
        s6.solve();
        Set<String> sol6 = new HashSet<String>();
        assertFalse(s6.getSolutions().equals(sol6));
        sol6.add("13");
        assertFalse(s6.getSolutions().equals(sol6));
        sol6.add("24");
        assertFalse(s6.getSolutions().equals(sol6));
        sol6.add("23");
        assertFalse(s6.getSolutions().equals(sol6));
        sol6.add("14");
        assertTrue(s6.getSolutions().equals(sol6));
    }

    @Test
    final void testCheckLCSs() {
        AllLCSsSolver s1 = new AllLCSsSolver("abcabcaa", "acbacba");
        s1.solve();
        Set<String> sol1 = new HashSet<String>();
        // L'insieme vuoto dà sempre true
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("acbaa");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("abcba");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("acaca");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("abaca");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("acaba");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("acbca");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("ababa");
        assertTrue(s1.checkLCSs(sol1));
        sol1.add("abab");
        assertFalse(s1.checkLCSs(sol1));
        sol1.remove("abab");
        sol1.add("12345");
        assertFalse(s1.checkLCSs(sol1));
        Set<String> sol2 = new HashSet<String>();
        sol2.add("");
        assertTrue(s1.checkLCSs(sol2));
    }

}
