/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

/**
 * 
 * Eccezione che segnala che un risolutore LCS ha commesso un errore.
 * 
 * @author Luca Tesei
 *
 */
public class LCSException extends RuntimeException {

    private static final long serialVersionUID = 7289728998235L;

    /**
     * 
     */
    public LCSException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public LCSException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public LCSException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public LCSException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public LCSException(Throwable cause) {
        super(cause);
    }

}
