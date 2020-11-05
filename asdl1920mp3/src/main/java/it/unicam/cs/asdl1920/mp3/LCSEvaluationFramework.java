/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Applica l'algoritmo di ricerca della più lunga sottosequenza comune a
 * sequenze di stringhe di lunghezza crescente. Per ogni lunghezza genera un
 * certo numero dato di sequenze casuali ed applica l'algoritmo LCS a tutte le
 * possibili coppie di queste sequenze. I dati relativi al tempo di esecuzione
 * in nanosecondi su ogni coppia di sequenze sono scritti su un file .csv (Comma
 * Separated Values). In un altro file .csv sono riportate le sequenze generate.
 * 
 * @author Luca Tesei
 *
 */
public class LCSEvaluationFramework {

    public static void main(String[] args) {
        String dirName = null;
        if (args.length > 0)
            dirName = args[0];
        else
            dirName = ".";
        // Variabili per il conteggio del tempo di esecuzione
        // Variabili per il conteggio del tempo di esecuzione
        long startTimeNano = 0;
        long elapsedTimeNano = 0;
        // Creo i file di output
        PrintStream o = null;
        PrintStream sequences = null;
        try {
            o = new PrintStream(new File(dirName + "/" + "evalfram.csv"));
            sequences = new PrintStream(
                    new File(dirName + "/" + "sequences.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Errore creazione file di ouput");
            System.exit(0);
        }
        // Creo una lista di stringhe per contenere le
        // sequenze su cui calcolare le LCSs
        List<String> list = new ArrayList<String>();
        // Inserisco la linea di intestazione dei dati nei file csv
        o.print("SeqId1,");
        o.print("SeqId2,");
        o.print("LCSTns");
        o.print("\n"); // Fine riga
        sequences.print("SeqId,");
        sequences.print("Sequence");
        sequences.print("\n");

        // Generazione delle sequenze e dei dati
        // Creo un generatore di numeri casuali da inserire nella sequenza
        Random randomGenerator = new Random();
        // Indice per le lunghezze
        int n;
        // Variabili di appoggio per la costruzione delle sequenze casuali
        int nucleotideCode = -1;
        char nucleotide = ' ';
        StringBuffer sb = null;
        // Ciclo esterno
        for (n = LCSEvaluationFrameworkParameters.MIN_LENGTH; n <= LCSEvaluationFrameworkParameters.MAX_LENGTH; n += LCSEvaluationFrameworkParameters.INCREMENTO_LUNGHEZZA) {
            // Genero le sequenze lunghe n
            for (int i = 0; i < LCSEvaluationFrameworkParameters.NUMBER_OF_SAMPLES_PER_LENGTH; i++) {
                // Scrivo in output il nome della sequenza
                sequences.print("seq" + "_" + n + "_" + i + ",");
                // Genero la i-esima sequenza casuale di nucleotidi lunga n
                sb = new StringBuffer();
                for (int j = 0; j < n; j++) {
                    nucleotideCode = randomGenerator.nextInt(4);
                    switch (nucleotideCode) {
                    case 0:
                        nucleotide = 'A';
                        break;
                    case 1:
                        nucleotide = 'G';
                        break;
                    case 2:
                        nucleotide = 'U';
                        break;
                    case 3:
                        nucleotide = 'C';
                        break;
                    }
                    sb.append(nucleotide);
                }
                // Aggiungo la sequenza alla lista
                list.add(sb.toString());
                // Salvo l'elemento sul file delle sequenze
                sequences.print(sb.toString());
                // Sequenza generata
                sequences.print("\n"); // Fine riga
                System.out.println(
                        "Sequence generated: " + "seq" + "_" + n + "_" + i);
            }
            // Creo un solver per ogni coppia di sequenze generate lunghe n
            for (int k = 0; k < list.size(); k++)
                for (int h = k + 1; h < list.size(); h++) {
                    // Creo due copie delle stringhe da usare per il solver
                    String s1 = new String(list.get(k));
                    String s2 = new String(list.get(h));
                    LCSSolver s = new LCSSolver(s1, s2);
                    // Guardo il tempo corrente in millisecondi e nanosecondi
                    startTimeNano = System.nanoTime();
                    // Chiamo l'algoritmo di folding
                    s.solve();
                    // Registro il tempo impiegato dall'algoritmo
                    elapsedTimeNano = System.nanoTime() - startTimeNano;
                    System.out.println("Found solution for sequences " + k
                            + " and " + h + " of length " + n);
                    // Check the solution
                    if (!s.isCommonSubsequence(s.getOneSolution())) {
                        // Errore
                        o.close();
                        sequences.close();
                        String message = "Errore nel calcolo di una sottosequenza comune per la coppia di sequenze "
                                + k + " e " + k + " di lunghezza " + n;
                        throw new LCSException(message);
                    }
                    // Scrivo sul file di output
                    o.print("seq" + "_" + n + "_" + k + ",");
                    o.print("seq" + "_" + n + "_" + h + ",");
                    o.print(elapsedTimeNano);
                    o.print("\n"); // Fine riga
                }
            // Azzero le liste
            list.clear();
        } // End for esterno
        o.close();
        sequences.close();
    } // end main

}
