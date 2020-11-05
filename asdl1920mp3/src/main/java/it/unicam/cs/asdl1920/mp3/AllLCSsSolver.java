/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.util.HashSet;
import java.util.Set;

/**
 * Un oggetto di questa classe è un risolutore del problema di trovare
 * <b>tutte</b> le più lunghe sottosequenze comuni tra due stringhe date.
 *
 * @author Enzo Di Nardo - enzo.dinardo@studenti.unicam.it
 */
public class AllLCSsSolver {

    private final String x;

    private final String y;

    private Set<String> solutions = null;


    /**
     * Costruisce un risolutore All LCS fra due stringhe date.
     *
     * @param x la prima stringa
     * @param y la seconda stringa
     * @throws NullPointerException se almeno una delle due stringhe passate
     *                              è nulla
     */
    public AllLCSsSolver(String x, String y) {
        if (x == null || y == null) {
            throw new NullPointerException("Almeno una delle due stringhe passate è null");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @return the string x
     */
    public String getX() {
        return x;
    }

    /**
     * @return the string y
     */
    public String getY() {
        return y;
    }

    /* Due solver sono uguali se e solo se hanno le stesse stringhe, in un
     * qualsiasi ordine esse siano date. Ad esempio, se in questo risolutore x =
     * "pippo" e y = "pluto allora un risolutore in cui x =
     * "pippo" e y = "pluto e un altro risolutore in cui x = "pluto" e y =
     * "pippo" sono entrambi da considerarsi uguale a questo risolutore.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllLCSsSolver allLCSSolver = (AllLCSsSolver) o;
        return (x.equals(allLCSSolver.x) && y.equals(allLCSSolver.y)) ||
                (x.equals(allLCSSolver.y) && y.equals(allLCSSolver.x));
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = result * y.hashCode() + x.hashCode() + y.hashCode();
        return result;
    }

    /**
     * Risolve il problema di trovare tutte le LCSs delle due stringhe di questo
     * solver, se non è stato già risolto precedentemente. Dopo l'esecuzione di
     * questo metodo il problema verrà considerato risolto.
     */
    public void solve() {
        if (!isSolved()) {
            solutions = backtrackAll(lCSLength(), x.length(), y.length());
        }
    }

    /**
     * Determina se questo solver ha già risolto il problema.
     *
     * @return true se il problema LCS di questo solver è già stato risolto
     * precedentemente, false altrimenti
     */
    public boolean isSolved() {
        return solutions != null;
    }

    /**
     * Determina la lunghezza massima delle sottosequenze comuni.
     *
     * @return la massima lunghezza delle sottosequenze comuni di this.x e
     * this.y.
     * @throws IllegalStateException se il solver non ha ancora risolto il
     *                               problema LCS
     */
    public int getLengthOfSolutions() {
        if (!isSolved()) {
            throw new IllegalStateException("Il solver non ha ancora risolto il problema LCS");
        }
        return solutions.iterator().next().length();
    }

    /**
     * Restituisce la soluzione del problema di tutte le LCSs.
     *
     * @return un insieme che contiene tutte le sottosequenze di this.x e this.y
     * di lunghezza massima
     * @throws IllegalStateException se il solver non ha ancora risolto il
     *                               problema All LCSs
     */
    public Set<String> getSolutions() {
        if (!isSolved()) {
            throw new IllegalStateException("Il solver non ha ancora risolto il problema All LCSs");
        }
        return solutions;
    }

    /**
     * Determina se una certa stringa è una sottosequenza comune delle due
     * stringhe di questo solver.
     *
     * @param z la string da controllare
     * @return true se z è sottosequenza di this.x e di this.y, false altrimenti
     * @throws NullPointerException se z è null
     */
    public boolean isCommonSubsequence(String z) {
        if (z == null) {
            throw new NullPointerException("La stringa passata è null");
        }
        // Una sequenza vuota è sempre una sottosequenza
        if (z.length() == 0) {
            return true;
        }
        int posZ = 0;
        int posX = 0;
        int posY = -1;
        for (; posX < x.length(); posX++) {
            // Se viene trovato il carattere nella sequenza x il ciclo for lo cerca nella sequenza y
            if (z.charAt(posZ) == x.charAt(posX)) {
                for (posY++; posY < y.length(); posY++) {
                    // Se viene trovato il carattere significa che è comune a entrambe le stringhe
                    if (z.charAt(posZ) == y.charAt(posY)) {
                        posZ++;
                        break;
                    }
                }
            }
            // True se tutti i caratteri sono stati trovati nelle sequenze x e y
            if (posZ == z.length()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determina se tutte le stringhe di un certo insieme hanno tutte la stessa
     * lunghezza e sono sottosequenze di entrambe le stringhe di questo solver.
     *
     * @param sequences l'insieme di stringhe da testare
     * @return true se tutte le stringe in sequences hanno la stessa lunghezza e
     * sono sottosequenze di this.x e this.y, false altrimenti
     * @throws NullPointerException se l'insieme passato è nullo.
     */
    public boolean checkLCSs(Set<String> sequences) {
        if (sequences == null) {
            throw new NullPointerException("L'insieme passato è nullo");
        }
        if (sequences.isEmpty()) {
            return true;
        }
        int len = sequences.iterator().next().length();
        for (String seq : sequences) {
            if (!this.isCommonSubsequence(seq) || seq.length() != len) {
                return false;
            }
        }
        return true;
    }

    /*
     * Genera una matrice delle lunghezze della sottosequenze comuni di
     * x e y, la lunghezza maggiore (LCS) si trova nella casella in basso
     * a destra
     *
     * @return la matrice generata
     */
    private int[][] lCSLength() {
        int m = x.length();
        int n = y.length();
        int[][] c = new int[m + 1][n + 1];
        // Inizializza la prima colonna a 0
        for (int i = 1; i <= m; i++) {
            c[i][0] = 0;
        }
        // Inizializza la prima riga a 0
        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }
        // Somma 1 alla casella della matrice quando i caratteri delle stringhe sono uguali
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                } else {
                    c[i][j] = c[i][j - 1];
                }
            }
        }
        return c;
    }

    /*
     * Genera l'insieme delle LCS partendo dalla matrice delle lunghezze
     * @param c
     *              la matrice
     *
     * @return una unsieme delle LCS
     */
    private Set<String> backtrackAll(int[][] C, int i, int j) {
        if (i == 0 || j == 0) {
            Set<String> firstSet = new HashSet<String>();
            firstSet.add("");
            return firstSet;
        } else if (x.charAt(i - 1) == y.charAt(j - 1)) {
            Set<String> tempSet = backtrackAll(C, i - 1, j - 1);
            Set<String> solSet = new HashSet<String>();
            // Concatena l'ultimo carattere alle stringhe incomplete nel tempSet
            for (String incomplete : tempSet) {
                solSet.add(incomplete + x.charAt(i - 1));
            }
            return solSet;
        } else {
            Set<String> solSet = new HashSet<String>();
            // Trova tutte le LCS nella posizione a sinistra nella matrice
            if (C[i][j - 1] >= C[i - 1][j]) {
                solSet = backtrackAll(C, i, j - 1);
            }
            // Trova tutte le LCS nella posizione a destra nella matrice
            if (C[i][j - 1] <= C[i - 1][j]) {
                solSet.addAll(backtrackAll(C, i - 1, j));
            }
            return solSet;
        }
    }

}
