/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

/**
 * Parametri generali del framework di valutazione degli algoritmi di folding.
 * 
 * @author Luca Tesei
 *
 */
public interface LCSEvaluationFrameworkParameters {
    /**
     * Lunghezza minima delle sequenze da generare
     */
    public static int MIN_LENGTH = 10;

    /**
     * Passo di incremento della lunghezza da MIN_LENGTH a MAX_LENGTH
     */
    public static int INCREMENTO_LUNGHEZZA = 10;

    /**
     * Lunghezza massima delle sequenze da generare
     */
    public static int MAX_LENGTH = 500;

    /**
     * Numero di sequenze da generare per lunghezza
     */
    public static int NUMBER_OF_SAMPLES_PER_LENGTH = 15;

}
