/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;


/**
 * Un oggetto di questa classe è un risolutore del problema della più lunga
 * sottosequenza comune tra due stringhe date.
 *
 * @author Enzo Di Nardo - enzo.dinardo@studenti.unicam.it
 */
public class LCSSolver {

    private final String x;

    private final String y;

    private String solution = null;


    /**
     * Costruisce un risolutore LCS fra due stringhe date.
     *
     * @param x la prima stringa
     * @param y la seconda stringa
     * @throws NullPointerException se almeno una delle due stringhe passate
     *                              è nulla
     */
    public LCSSolver(String x, String y) {
        if (x == null || y == null) {
            throw new NullPointerException("Una delle due stringhe passate è null");
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
        LCSSolver lcsSolver = (LCSSolver) o;
        return (x.equals(lcsSolver.x) && y.equals(lcsSolver.y)) ||
                (x.equals(lcsSolver.y) && y.equals(lcsSolver.x));
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = result * y.hashCode() + x.hashCode() + y.hashCode();
        return result;
    }

    /**
     * Risolve il problema LCS delle due stringhe di questo solver, se non è
     * stato già risolto precedentemente. Dopo l'esecuzione di questo metodo il
     * problema verrà considerato risolto.
     */
    public void solve() {
        if (!isSolved()) {
            this.solution = backtrack(lCSLength());
        }
    }

    /**
     * Determina se questo solver ha già risolto il problema.
     *
     * @return true se il problema LCS di questo solver è già stato risolto
     * precedentemente, false altrimenti
     */
    public boolean isSolved() {
        return this.solution != null;
    }

    /**
     * Determina la lunghezza massima delle sottosequenze comuni.
     *
     * @return la massima lunghezza delle sottosequenze comuni di x e y.
     * @throws IllegalStateException se il solver non ha ancora risolto il
     *                               problema LCS
     */
    public int getLengthOfSolution() {
        if (!isSolved()) {
            throw new IllegalStateException("Il solver non ha ancora risolto il problema LCS");
        }
        return solution.length();
    }

    /**
     * Restituisce una soluzione del problema LCS.
     *
     * @return una sottosequenza di this.x e this.y di lunghezza massima
     * @throws IllegalStateException se il solver non ha ancora risolto il
     *                               problema LCS
     */
    public String getOneSolution() {
        if (!isSolved()) {
            throw new IllegalStateException("Il solver non ha ancora risolto il problema LCS");
        }
        return this.solution;
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
     * Genera la LCS partendo dalla matrice delle lunghezze
     * @param c
     *              la matrice
     *
     * @return una stringa LCS
     */
    private String backtrack(int[][] c) {
        int i = x.length();
        int j = y.length();
        StringBuffer sol = new StringBuffer();

        while (i > 0 && j > 0) {
            if (x.charAt(i - 1) == y.charAt(j - 1)) {
                sol.insert(0, x.charAt(i - 1));
                i--;
                j--;
            } else {
                if (c[i - 1][j] >= c[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }
        return sol.toString();
    }

}
